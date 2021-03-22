package org.app.opengl_es_android_version.object.object2d.demo;

import android.content.Context;
import android.opengl.GLES20;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.contant.Constants;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.object.object2d.Object2D;
import org.app.opengl_es_android_version.util.ShaderHelper;

import static android.opengl.GLES20.GL_FLOAT;

/***
 * 用折线来绘制一个圆形
 */
public class CoordinateLines implements Object2D {

    private static final int POSITION_COMPONENT_COUNT = 2;

    private int programId;
    private int uColorLocation;
    private int aPositionLocation;

    final private int count;

    VertexArray vertexArray;

    public CoordinateLines() {
        vertexArray = new VertexArray(CoordinateLines);
        count = vertexArray.getFloatBuffer().limit() / 2;
    }

    float r = 1.0f;

    private float[] CoordinateLines = {
            -r, 0f,
            r, 0f,
            0, r,
            0, -r
    };


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
        GLES20.glVertexAttribPointer(aPositionLocation,
                POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexArray.getFloatBuffer());
        //使能顶点数组
        GLES20.glEnableVertexAttribArray(aPositionLocation);
        vertexArray.getFloatBuffer().position(0);
    }

    @Override
    public void draw() {
        GLES20.glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 0.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, count);
    }
}
