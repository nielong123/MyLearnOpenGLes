package org.app.opengl_es_android_version.object.object3d;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.program.TextureShaderProgram;
import org.app.opengl_es_android_version.util.TextureHelper;

import static org.app.opengl_es_android_version.contant.Constants.BYTES_PER_FLOAT;

public class TestTable3D extends Object3D {

    TextureShaderProgram shaderProgram;

    private int textureId;


    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT)
            * BYTES_PER_FLOAT;

    private static final float[] VERTEX_DATA = {
            //x,    y,      s,      t
            0f, 0f, 0.5f, 0.5f,
            -0.5f, -0.8f, 0f, 0.9f,
            0.5f, -0.8f, 1f, 0.9f,
            0.5f, 0.8f, 1f, 0.1f,
            -0.5f, 0.8f, 0f, 0.1f,
            -0.5f, -0.8f, 0f, 0.9f,
    };

    //模型矩阵
    public float[] modelMatrix = new float[16];

    private final VertexArray vertexArray;

    public TestTable3D(Context context) {
        super(context);
        vertexArray = new VertexArray(VERTEX_DATA);
        Matrix.setIdentityM(modelMatrix, 0);
    }

    @Override
    public void bindData(Context context) {
        super.bindData(context);
        textureId = TextureHelper.loadTexture(context, R.drawable.map1);
        shaderProgram = new TextureShaderProgram(context);
    }

    @Override
    public void unbind() {
        GLES20.glDisableVertexAttribArray(shaderProgram.aPositionLocation);
        GLES20.glDisableVertexAttribArray(shaderProgram.aTextureCoordinatesLocation);
    }

    @Override
    public void draw() {
//        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        vertexArray.setVertexAttributePointer(
                shaderProgram.aPositionLocation,
                POSITION_COMPONENT_COUNT,
                STRIDE,
                0
        );
        vertexArray.setVertexAttributePointer(
                shaderProgram.aTextureCoordinatesLocation,
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE,
                POSITION_COMPONENT_COUNT
        );
        shaderProgram.userProgram();
        shaderProgram.setUniforms(mvpMatrix, textureId);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);
        GLES20.glDisable(GLES20.GL_TEXTURE_2D);
//        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
    }

}
