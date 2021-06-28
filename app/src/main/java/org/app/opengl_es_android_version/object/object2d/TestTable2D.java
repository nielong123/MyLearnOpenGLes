package org.app.opengl_es_android_version.object.object2d;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.program.TextureShaderProgram;
import org.app.opengl_es_android_version.util.TextureHelper;

import java.util.Arrays;

import static org.app.opengl_es_android_version.contant.Constants.BYTES_PER_FLOAT;

public class TestTable2D extends Object2D {

    private static final String TAG = TestTable2D.class.getSimpleName();
    TextureShaderProgram shaderProgram;

    private int textureId;


    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT)
            * BYTES_PER_FLOAT;

    static final float vf = 1.0f;
    private static final float[] VERTEX_DATA = {
            //x,    y,      s,      t
            0f, 0f, 0.5f, 0.5f,
            -vf, -vf, 0f, 0.9f,
            vf, -vf, 1f, 0.9f,
            vf, vf, 1f, 0.1f,
            -vf, vf, 0f, 0.1f,
            -vf, -vf, 0f, 0.9f,
    };

    private final VertexArray vertexArray;
    private final VertexArray textureArray;

    public TestTable2D(Context context) {
        super(context);
        vertexArray = new VertexArray(VERTEX_DATA);
        textureArray = new VertexArray(VERTEX_DATA);
    }

    @Override
    public void bindData(Context context) {
        super.bindData(context);
        textureId = TextureHelper.loadTexture(context, R.drawable.map1);
        shaderProgram = new TextureShaderProgram(context);
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
        textureArray.setVertexAttributePointer(
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

    @Override
    public void unbind() {
        GLES20.glDisableVertexAttribArray(shaderProgram.aPositionLocation);
        GLES20.glDisableVertexAttribArray(shaderProgram.aTextureCoordinatesLocation);
    }

    public float[] getRect() {
        float[] resultMatrix = new float[16];
        Matrix.multiplyMM(resultMatrix, 0, mvpMatrix, 0, VERTEX_DATA, 0);
        Log.e(TAG, "getRect: " + Arrays.toString(resultMatrix));
        return resultMatrix;
    }

}
