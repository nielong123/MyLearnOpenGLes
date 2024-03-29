package org.app.opengl_es_android_version.object.object2d;

import android.content.Context;
import android.opengl.GLES20;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.util.ColorHelper;
import org.app.opengl_es_android_version.util.Geometry;

import java.nio.ByteBuffer;


/***
 * 在绘制圆的基础上绘制一个五角星，用于测试glDrawElement()
 */
public class Star5P extends Object2D {

    //因为是x和y，所以两个值代表一个属性
    private static final int POSITION_COMPONENT_COUNT = 2;

    private VertexArray vertexArray;

    private int count = 0;

    private ByteBuffer indexArray = ByteBuffer.allocateDirect(5)
            .put(new byte[]{
                    0, 2, 4, 1, 3
            });


    public Star5P() {
        super();
        Geometry.Point point = new Geometry.Point(0.2f, 0.1f, 0f);
        Geometry.Star5Points start = new Geometry.Star5Points(point, 0.6f);
        vertexArray = new VertexArray(getVertexWithStart(start));
        count = vertexArray.getFloatBuffer().limit() / 2;
        indexArray.position(0);
    }

    private float[] getVertexWithStart(Geometry.Star5Points start) {
        int all = 360;

        int angle = 360 / 5;

        int count = (all / angle) * 2;
        float[] vertexs = new float[count];
        int offset = 0;
        for (int angdeg = 0; angdeg < all; angdeg += angle) {
            double radians = Math.toRadians(angdeg);
            vertexs[offset++] = (float) (start.center.x - start.radius * Math.sin(radians));
            vertexs[offset++] = (float) (start.center.y + start.radius * Math.cos(radians));
        }

        return vertexs;
    }

    @Override
    public void bindData(Context context) {
        super.bindData(context);
    }

    @Override
    public void draw() {
        colorShaderProgram.userProgram();
        GLES20.glUniformMatrix4fv(colorShaderProgram.aMatrixLocation, 0, false, mvpMatrix, 0);
        GLES20.glVertexAttribPointer(colorShaderProgram.aPositionLocation,
                POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, 0, vertexArray.getFloatBuffer());
        //使能顶点数组
        GLES20.glEnableVertexAttribArray(colorShaderProgram.aPositionLocation);
        ColorHelper.setColor(colorShaderProgram.aColorLocation, context.getColor(R.color.colorAccent));
        GLES20.glDrawElements(GLES20.GL_LINE_LOOP, count, GLES20.GL_UNSIGNED_BYTE, indexArray);
        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, count);
    }

    @Override
    public void unbind() {
        GLES20.glUseProgram(0);
//        colorShaderProgram.delProgram();
        GLES20.glDisableVertexAttribArray(colorShaderProgram.aPositionLocation);
    }

}
