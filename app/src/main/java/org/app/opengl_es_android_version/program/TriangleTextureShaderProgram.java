package org.app.opengl_es_android_version.program;

import android.content.Context;
import android.opengl.GLES20;

import org.app.opengl_es_android_version.R;

public class TriangleTextureShaderProgram extends ShaderProgram {

    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";
    public final int uMatrixLocation;
    public final int uTextureUnitLocation;

    protected static final String A_POSITION = "a_Position";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";
    public final int aPositionLocation;
    public final int aTextureCoordinatesLocation;


    public TriangleTextureShaderProgram(Context context) {
        super(context, R.raw.simple_vertex_shader1_5, R.raw.simple_fragment_shader1_5);
        uMatrixLocation = GLES20.glGetUniformLocation(programId, U_MATRIX);
        uTextureUnitLocation = GLES20.glGetUniformLocation(programId, U_TEXTURE_UNIT);

        aPositionLocation = GLES20.glGetAttribLocation(programId, A_POSITION);
        aTextureCoordinatesLocation = GLES20.glGetAttribLocation(programId, A_TEXTURE_COORDINATES);
    }

    public void setUniforms(float[] matrix, int textureId) {
        // 传入变化矩阵到shaderProgram
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
        //激活纹理单元0
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        //绑定纹理对象ID
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        //告诉shaderProgram sampler 2d纹理采集器，使用纹理单元0的纹理对象
        GLES20.glUniform1i(uTextureUnitLocation, 0);
    }

}
