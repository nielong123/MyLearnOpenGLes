package org.app.opengl_es_android_version.object.object2d;

import android.content.Context;
import android.opengl.GLES20;

import org.app.opengl_es_android_version.data.VertexArray;

import static android.opengl.GLES20.GL_FLOAT;


/***
 * 绘制折线
 */
public class Polyline extends Object2D {

    private static final int POSITION_COMPONENT_COUNT = 2;

    VertexArray vertexArray;

    public Polyline() {
        super();
        vertexArray = new VertexArray(tableVerticesWithPolyline);
    }

    private float[] tableVerticesWithPolyline = {

            -0.3f, -0.4f,
            0.1f, 0.1f,
            -0.35f, 0.5f
    };


    @Override
    public void bindData(Context context) {
        super.bindData(context);
    }

    @Override
    public void unbind() {
        GLES20.glUseProgram(0);
        GLES20.glDisableVertexAttribArray(colorShaderProgram.aPositionLocation);
    }

    @Override
    public void draw() {
        colorShaderProgram.userProgram();
        //告诉opengl从缓冲区vertextData中取数据找到属性a_Position的数据
        GLES20.glVertexAttribPointer(colorShaderProgram.aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexArray.getFloatBuffer());
//        vertexArray.getFloatBuffer().position(0);
        //使能顶点数组
        GLES20.glEnableVertexAttribArray(colorShaderProgram.aPositionLocation);
        GLES20.glUniform4f(colorShaderProgram.aColorLocation, 1.0f, 0.3f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINE_STRIP, 0, 3);
    }

}
