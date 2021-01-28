package org.app.opengl_es_android_version.object;

import android.opengl.GLES20;
import android.opengl.Matrix;

import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.program.TextureShaderProgram;

import static org.app.opengl_es_android_version.contant.Constants.BYTES_PER_FLOAT;

public class Table {

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

    public float[] modelMatrix = new float[16];

    private final VertexArray vertexArray;

    public Table() {
        vertexArray = new VertexArray(VERTEX_DATA);
        Matrix.setIdentityM(modelMatrix, 0);
    }

    public void bindData(TextureShaderProgram shaderProgram) {
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
    }

    public void draw() {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);
    }
}
