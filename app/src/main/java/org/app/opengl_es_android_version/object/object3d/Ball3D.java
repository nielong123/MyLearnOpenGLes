package org.app.opengl_es_android_version.object.object3d;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.util.ColorHelper;
import org.app.opengl_es_android_version.util.Geometry;

import java.util.ArrayList;

public class Ball3D extends Object3D {

    Geometry.Circle circle;
    int color = -1;

    private int count;

    public Ball3D() {
        super();
        circle = new Geometry.Circle(new Geometry.Point(0.0f, 0.0f, 0.0f), 0.6f);
        circle.angle = 20;
        count = initVertex();
    }

    public Ball3D(float x, float y, float z, float radius) {
        super();
        circle = new Geometry.Circle(new Geometry.Point(x, y, z), radius);
        circle.angle = 20;
        count = initVertex();
    }

    public Ball3D(float x, float y, float z, float radius, int color) {
        super();
        this.color = color;
        circle = new Geometry.Circle(new Geometry.Point(x, y, z), radius);
        circle.angle = 20;
        count = initVertex();
    }

    private int initVertex() {

        ArrayList<Float> alVertix = new ArrayList<Float>();// 存放顶点坐标的ArrayList
        final int angleSpan = (int) circle.angle;// 将球进行单位切分的角度
        final float r = circle.radius;
        final float x = circle.center.x;
        final float y = circle.center.y;
        final float z = circle.center.z;
        // 垂直方向angleSpan度一份
        for (int vAngle = -90; vAngle < 90; vAngle = vAngle + angleSpan) {
            // 水平方向angleSpan度一份
            for (int hAngle = 0; hAngle <= 360; hAngle = hAngle + angleSpan) {
                // 纵向横向各到一个角度后计算对应的此点在球面上的坐标
                float x0 = x + (float) (r * Math.cos(Math.toRadians(vAngle)) * Math.cos(Math.toRadians(hAngle)));
                float y0 = y + (float) (r * Math.cos(Math.toRadians(vAngle)) * Math.sin(Math.toRadians(hAngle)));
                float z0 = z + (float) (r * Math.sin(Math.toRadians(vAngle)));

                float x1 = x + (float) (r * Math.cos(Math.toRadians(vAngle)) * Math.cos(Math.toRadians(hAngle + angleSpan)));
                float y1 = y + (float) (r * Math.cos(Math.toRadians(vAngle)) * Math.sin(Math.toRadians(hAngle + angleSpan)));
                float z1 = z + (float) (r * Math.sin(Math.toRadians(vAngle)));

                float x2 = x + (float) (r * Math.cos(Math.toRadians(vAngle + angleSpan)) * Math.cos(Math.toRadians(hAngle + angleSpan)));
                float y2 = y + (float) (r * Math.cos(Math.toRadians(vAngle + angleSpan)) * Math.sin(Math.toRadians(hAngle + angleSpan)));
                float z2 = z + (float) (r * Math.sin(Math.toRadians(vAngle + angleSpan)));

                float x3 = x + (float) (r * Math.cos(Math.toRadians(vAngle + angleSpan)) * Math.cos(Math.toRadians(hAngle)));
                float y3 = y + (float) (r * Math.cos(Math.toRadians(vAngle + angleSpan)) * Math.sin(Math.toRadians(hAngle)));
                float z3 = z + (float) (r * Math.sin(Math.toRadians(vAngle + angleSpan)));

                // 将计算出来的XYZ坐标加入存放顶点坐标的ArrayList
                alVertix.add(x1);
                alVertix.add(y1);
                alVertix.add(z1);
                alVertix.add(x3);
                alVertix.add(y3);
                alVertix.add(z3);
                alVertix.add(x0);
                alVertix.add(y0);
                alVertix.add(z0);

                alVertix.add(x1);
                alVertix.add(y1);
                alVertix.add(z1);
                alVertix.add(x2);
                alVertix.add(y2);
                alVertix.add(z2);
                alVertix.add(x3);
                alVertix.add(y3);
                alVertix.add(z3);

                /*
                2---------------3
                |             / |
                |          /    |
                |       /       |
                |    /          |
                | /             |
                1---------------0
                 */
            }
        }
        float[] vertex = new float[alVertix.size()];
        for (int i = 0; i < alVertix.size(); i++) {
            vertex[i] = alVertix.get(i);
        }
        vertexArray = new VertexArray(vertex);
        return alVertix.size() / 3;
    }

    @Override
    public void bindData(Context context) {
        super.bindData(context);

    }

    @Override
    protected void draw() {

        ColorHelper.setColor(colorShaderProgram.aColorLocation, color);
        GLES20.glUniformMatrix4fv(colorShaderProgram.aMatrixLocation, 1, false, mvpMatrix, 0);
        GLES20.glVertexAttribPointer(colorShaderProgram.aPositionLocation, 3, GLES20.GL_FLOAT,
                false, 3 * 4, vertexArray.getFloatBuffer());

        GLES20.glEnableVertexAttribArray(colorShaderProgram.aPositionLocation);
        GLES20.glDrawArrays(GLES20.GL_LINE_STRIP, 0, count);
    }

    @Override
    public void unbind() {
        GLES20.glDisableVertexAttribArray(colorShaderProgram.aPositionLocation);
    }

    public Geometry.Point getCenterPoint() {
        return circle.center;
    }

    public Geometry.Point getCoordinate() {
        float[] resultVec = new float[4];
        float[] rhsVec = new float[]{circle.center.x, circle.center.y, circle.center.z, 0};
        Matrix.multiplyMV(resultVec, 0, modelMatrix, 0, rhsVec, 0);
        return new Geometry.Point(resultVec[0], resultVec[1], resultVec[2]);
    }
}
