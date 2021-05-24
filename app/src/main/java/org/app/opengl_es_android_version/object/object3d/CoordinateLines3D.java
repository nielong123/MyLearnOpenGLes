package org.app.opengl_es_android_version.object.object3d;

import android.content.Context;
import android.opengl.GLES20;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.contant.Constants;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.util.ShaderHelper;

import static android.opengl.GLES20.GL_FLOAT;

/***
 * 用折线来绘制一个圆形
 */
public class CoordinateLines3D extends Object3D {

    private static final int POSITION_COMPONENT_COUNT = 3;


    final private int count;

    public CoordinateLines3D(Context context) {
        super(context);
        vertexArray = new VertexArray(CoordinateLines);
        count = vertexArray.getFloatBuffer().limit() / 3;

    }

    float r = 3.0f;

    private float[] CoordinateLines = {
            0f, r, 0f,
            0f, -r, 0f,
            -r, 0f, 0f,
            r, 0f, 0f,
            0, 0f, -r,
            -0, 0f, r
    };

    @Override
    public void bindData(Context context) {
        super.bindData(context);
        programId = ShaderHelper.buildProgram(context,
                R.raw.texture_vertex_shader_copy, R.raw.simple_fragment_shader1_5);
        GLES20.glUseProgram(programId);
        //获取uniform的位置，把位置存入uColorLocation中
        uColorLocation = GLES20.glGetUniformLocation(programId, Constants.U_COLOR);
        //获取属性位置
        aPositionLocation = GLES20.glGetAttribLocation(programId, Constants.A_POSITION);
        //获取矩阵属性
        aMatrixLocation = GLES20.glGetUniformLocation(programId, Constants.U_MATRIX);

    }

    @Override
    protected void draw() {
        GLES20.glUniformMatrix4fv(aMatrixLocation, 1, false, mvpMatrix, 0);
        //告诉opengl从缓冲区vertextData中取数据找到属性a_Position的数据
        GLES20.glVertexAttribPointer(aPositionLocation,
                POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexArray.getFloatBuffer());
        //使能顶点数组
        GLES20.glEnableVertexAttribArray(aPositionLocation);
        GLES20.glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, count);
//        GLES20.glUniform4f(uColorLocation, 0.3f, 0.3f, 0.3f, 1.0f);
//        GLES20.glDrawArrays(GLES20.GL_LINES, 2, 2);
//        GLES20.glUniform4f(uColorLocation, 0.7f, 0.7f, 0.7f, 1.0f);
//        GLES20.glDrawArrays(GLES20.GL_LINES, 4, 2);
    }

    @Override
    public void unbind() {
//        GLES20.glDisableVertexAttribArray(uColorLocation);
        GLES20.glDisableVertexAttribArray(aPositionLocation);
//        GLES20.glDisableVertexAttribArray(aMatrixLocation);
    }

}
