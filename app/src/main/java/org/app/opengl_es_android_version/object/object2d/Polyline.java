package org.app.opengl_es_android_version.object.object2d;

import android.content.Context;
import android.opengl.GLES20;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.contant.Constants;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.util.ShaderHelper;


//todo  2021-3-11
public class Polyline implements Object2D {

    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int STRIDE = POSITION_COMPONENT_COUNT * Constants.POSITION_COMPONENT_COUNT;

    private int programId;
    private int uColorLocation;
    private int aPositionLocation;

    VertexArray vertexArray;

    public Polyline() {
        vertexArray = new VertexArray(tableVerticesWithPolyline);
    }

    private float[] tableVerticesWithPolyline = {

            -0.5f, -0.5f,
            0.5f, 0.5f,
            -0.5f, 0.5f
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
        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, 3);
    }
}
