package org.app.opengl_es_android_version.object.object2d;

import android.content.Context;
import android.opengl.GLES20;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.contant.Constants;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.util.Geometry;
import org.app.opengl_es_android_version.util.ShaderHelper;

/***
 * 用折线来绘制一个圆形
 */
public class Circle implements Object2D {

    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int STRIDE = POSITION_COMPONENT_COUNT * Constants.POSITION_COMPONENT_COUNT;

    private int programId;
    private int uColorLocation;
    private int aPositionLocation;

    final private int count;

    Geometry.Circle circle;

    VertexArray vertexArray;

    public Circle() {
        Geometry.Point point = new Geometry.Point(0f, 0f, 0f);
        circle = new Geometry.Circle(point, 0.5f);
//        vertexArray = new VertexArray(tableVerticesWithPolyline);
        vertexArray = new VertexArray(getVertexWithCircle(circle));
        count = vertexArray.getFloatBuffer().limit() / 2;
    }

    private float[] tableVerticesWithPolyline = {

            -0.5f, -0.5f,
            0.5f, 0.5f,
            -0.35f, 0.5f
    };


    private float[] getVertexWithCircle(Geometry.Circle circle) {

        int count = (int) (360 / circle.angdeg) * 2;
        float[] vertexs = new float[count];
        int offset = 0;
        for (int i = 1; i < 360 / circle.angdeg; i++) {
            double radians = Math.toRadians(circle.angdeg * i);
            vertexs[offset++] = (float) (circle.center.x + circle.radius * Math.cos(radians));
            vertexs[offset++] = (float) (circle.center.y + circle.radius * Math.sin(radians));
        }

        return vertexs;
    }


    @Override
    public void bindData(Context context) {
        programId = ShaderHelper.buildProgram(context,
                R.raw.simple_vertex_shader1_5, R.raw.simple_fragment_shader1_5);
        GLES20.glUseProgram(programId);
        //获取uniform的位置，把位置存入uColorLocation中
        uColorLocation = GLES20.glGetUniformLocation(programId, Constants.U_COLOR);
        //获取属性位置
        aPositionLocation = GLES20.glGetAttribLocation(programId, Constants.A_POSITION);
        //告诉opengl从缓冲区vertextData中取数据找到属性a_Position的数据
//        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData);
        //使能顶点数组
        GLES20.glEnableVertexAttribArray(aPositionLocation);
        vertexArray.setVertexAttributePointer(
                0,
                POSITION_COMPONENT_COUNT,
                STRIDE,
                0
        );
    }

    @Override
    public void draw() {
        GLES20.glUniform4f(uColorLocation, 1.0f, 3.0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, count);
    }
}
