package org.app.opengl_es_android_version.program;

import android.content.Context;
import android.opengl.GLES20;

import org.app.opengl_es_android_version.R;

public class CubeShaderProgram extends ShaderProgram {

    protected static final String U_MATRIX = "u_Matrix";
    public final int uMatrixLocation;
    protected static final String A_POSTIION = "a_Position";
    public final int uPositionLocation;
    protected static final String A_COLOR = "a_Color";
    public final int aColorLocation;


    public CubeShaderProgram(Context context) {
        super(context, R.raw.cube_one_vertex_shader, R.raw.cube_one_fragment_shader);
        this.uMatrixLocation = GLES20.glGetUniformLocation(programId, U_MATRIX);
        this.uPositionLocation = GLES20.glGetAttribLocation(programId, A_POSTIION);
        this.aColorLocation = GLES20.glGetAttribLocation(programId, A_COLOR);
    }

    public void setUniforms(float[] matrix) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
    }
}
