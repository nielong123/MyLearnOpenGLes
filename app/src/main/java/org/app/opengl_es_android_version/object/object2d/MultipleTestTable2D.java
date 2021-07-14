package org.app.opengl_es_android_version.object.object2d;

import android.content.Context;
import android.opengl.GLES20;

import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.program.TextureShaderProgram;
import org.app.opengl_es_android_version.util.ErrorUtils;
import org.app.opengl_es_android_version.util.Geometry;

public class MultipleTestTable2D extends Object2D {

    private static final String TAG = MultipleTestTable2D.class.getSimpleName();

    private Integer textureId;

    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;

    private static final float[] TEXTURE_DATA = {
            0.5f, 0.5f,
            0f, 1f,
            1f, 1f,
            1f, 0f,
            0f, 0f,
            0f, 1f,
    };


    private final VertexArray vertexArray;
    private final VertexArray textureArray;

    public MultipleTestTable2D(int textureId, float[] vertexData) {
        super();
        this.textureId = textureId;
        vertexArray = new VertexArray(vertexData);
        textureArray = new VertexArray(TEXTURE_DATA);
    }

    public MultipleTestTable2D(int textureId, Geometry.Rect rect) {
        super();
        this.textureId = textureId;
        vertexArray = new VertexArray(getVertexData(rect));
        textureArray = new VertexArray(TEXTURE_DATA);
    }

    public void setTextureShaderProgram(TextureShaderProgram textureShaderProgram) {
        this.textureShaderProgram = textureShaderProgram;
    }

    @Override
    public void bindData(Context context) {
        super.bindData(context);
    }

    @Override
    public void draw() {

        textureShaderProgram.userProgram();
        vertexArray.enableVertexAttributePointer(
                textureShaderProgram.aPositionLocation,
                POSITION_COMPONENT_COUNT,
                16,
                0
        );

        textureArray.enableVertexAttributePointer(
                textureShaderProgram.aTextureCoordinatesLocation,
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                8,
                0
        );
        textureShaderProgram.userProgram();
        textureShaderProgram.setUniforms(mvpMatrix, textureId);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 6);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, GLES20.GL_NONE);
        ErrorUtils.getError();
    }

    @Override
    public void unbind() {
        GLES20.glUseProgram(0);
        GLES20.glDisableVertexAttribArray(textureShaderProgram.aPositionLocation);
        GLES20.glDisableVertexAttribArray(textureShaderProgram.aTextureCoordinatesLocation);
    }

    public float[] getRect() {
        float[] resultMatrix = new float[16];
//        Matrix.multiplyMM(resultMatrix, 0, mvpMatrix, 0, VERTEX_DATA, 0);
        return resultMatrix;
    }

    private float[] getVertexData(Geometry.Rect rect) {
        float[] resultMatrix = new float[24];
        if (rect == null) return resultMatrix;
        resultMatrix[0] = (rect.left + rect.right) / 2;
        resultMatrix[1] = (rect.top + rect.bottom) / 2;

        resultMatrix[4] = rect.left;
        resultMatrix[5] = rect.bottom;

        resultMatrix[8] = rect.right;
        resultMatrix[9] = rect.bottom;

        resultMatrix[12] = rect.right;
        resultMatrix[13] = rect.top;

        resultMatrix[16] = rect.left;
        resultMatrix[17] = rect.top;

        resultMatrix[20] = rect.left;
        resultMatrix[21] = rect.bottom;


        return resultMatrix;
    }


}
