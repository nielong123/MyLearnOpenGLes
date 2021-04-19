package org.app.opengl_es_android_version.object.object3d;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.contant.Constants;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.object.object2d.Object2D;
import org.app.opengl_es_android_version.util.ShaderHelper;

import static android.opengl.GLES20.GL_FLOAT;

/***
 * 用折线来绘制一个圆形
 */
public class CoordinateLines3D extends Object2D {

    private static final int POSITION_COMPONENT_COUNT = 3;

    private int programId;
    private int aMatrixLocation;
    private int uColorLocation;
    private int aPositionLocation;

    final private int count;

    VertexArray vertexArray;

    public CoordinateLines3D() {
        super();
        vertexArray = new VertexArray(CoordinateLines);
        count = vertexArray.getFloatBuffer().limit() / 3;

//        modelMatrix = new float[]{
//                1, 0, 0.1f, 0,
//                0, 1, 0.5f, 0,
//                0, 0.1f, 1, 0,
//                0, 0, 0, 1
//        };
    }

    float r = 3.0f;

    private float[] CoordinateLines = {
            -r, 0f, 0f,
            r, 0f, 0f,
            0f, r, 0f,
            0f, -r, 0f,
            0, 0f, -r,
            -0, 0f, r
    };

//    private float[] CoordinateLines = {
//            -r, 0f,
//            r, 0f,
//            0, r,
//            0, -r
//    };

    @Override
    public void bindData(Context context) {
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
    public void draw() {
        GLES20.glUniformMatrix4fv(aMatrixLocation, 1, false, mvpMatrix, 0);
        //告诉opengl从缓冲区vertextData中取数据找到属性a_Position的数据
        GLES20.glVertexAttribPointer(aPositionLocation,
                POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexArray.getFloatBuffer());
        //使能顶点数组
        GLES20.glEnableVertexAttribArray(aPositionLocation);
        GLES20.glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 0.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, count);
    }

    @Override
    public void draw(float[] viewProjectMatrix) {
        Matrix.multiplyMM(mvpMatrix, 0, viewProjectMatrix, 0, modelMatrix, 0);
        draw();
    }
}
