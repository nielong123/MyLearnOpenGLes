package org.app.opengl_es_android_version.object.object2d;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.contant.Constants;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.util.ShaderHelper;

import static android.opengl.GLES20.GL_FLOAT;

public class RectangleWithTexture implements Object2D {

    private static final int POSITION_COMPONENT_COUNT = 4;
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

    private static final float c = 1f;

    private static final float[] TEXTURE_DATA = {
            0f, c,
            0f, 0f,
            c, c,
            c, 0f
    };

    //模型矩阵
    public float[] modelMatrix = new float[16];

    private final VertexArray vertexArray;

    public RectangleWithTexture() {
        vertexArray = new VertexArray(VERTEX_DATA);
        Matrix.setIdentityM(modelMatrix, 0);
    }

    @Override
    public void bindData(Context context) {
        programId = ShaderHelper.buildProgram(context,
                R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);
        GLES20.glUseProgram(programId);




        //获取属性位置
        aPositionLocation = GLES20.glGetAttribLocation(programId, Constants.A_POSITION);
        //告诉opengl从缓冲区vertextData中取数据找到属性a_Position的数据
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexArray.getFloatBuffer());
        vertexArray.getFloatBuffer().position(0);
        //使能顶点数组
        GLES20.glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void draw() {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);
    }
}
