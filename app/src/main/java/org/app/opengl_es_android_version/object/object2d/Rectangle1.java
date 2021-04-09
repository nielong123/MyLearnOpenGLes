package org.app.opengl_es_android_version.object.object2d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.Matrix;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.contant.Constants;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.util.ShaderHelper;

import java.nio.ByteBuffer;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_UNSIGNED_BYTE;

public class Rectangle1 implements Object2D {

    private static final int POSITION_COMPONENT_COUNT = 2;
    private int uColorLocation;
    private int aPositionLocation;

    private int programId;

    private static final float r = 0.3f;

    private static final float[] VERTEX_DATA = {
            -r, -r,
            r, -r,
            r, r,
            -r, r
    };

    private ByteBuffer indexArray = ByteBuffer.allocateDirect(6)
            .put(new byte[]{
                    0, 1, 2, 2, 3, 0
            });

    //模型矩阵
    public float[] modelMatrix = new float[16];

    private final VertexArray vertexArray;

    public Rectangle1(Context context) {
        vertexArray = new VertexArray(VERTEX_DATA);
        Matrix.setIdentityM(modelMatrix, 0);
        indexArray.position(0);
    }

    @Override
    public void bindData(Context context) {
        programId = ShaderHelper.buildProgram(context,
                R.raw.simple_vertex_shader1_5, R.raw.simple_fragment_shader1_5);
        GLES20.glUseProgram(programId);


        //获取属性位置
        uColorLocation = GLES20.glGetUniformLocation(programId, Constants.U_COLOR);
        //获取属性位置
        aPositionLocation = GLES20.glGetAttribLocation(programId, Constants.A_POSITION);
        //告诉opengl从缓冲区vertextData中取数据找到属性a_Position的数据
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexArray.getFloatBuffer());
//        vertexArray.getFloatBuffer().position(0);
        //使能顶点数组
        GLES20.glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void draw() {
        GLES20.glUniform4f(uColorLocation, 1.0f, 3.0f, 0.6f, 1.0f);
        GLES20.glDrawElements(GLES20.GL_TRIANGLE_FAN, indexArray.limit(), GL_UNSIGNED_BYTE, indexArray);
    }
}
