package org.app.opengl_es_android_version.program;

import android.content.Context;
import android.opengl.GLES20;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.util.ColorHelper;

public class ColorShaderProgram extends ShaderProgram {

    protected static final String U_MATRIX = "u_Matrix";
    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String U_COLOR = "u_Color";

    public final int uMatrixLocation;
    public final int aPositionLocation;
    public final int aColorLocation;
    public final int uColorLocation;


    public ColorShaderProgram(Context context) {
        super(context, R.raw.simple_vertex_shader, R.raw.simple_fragment_shader);

        uMatrixLocation = GLES20.glGetUniformLocation(programId, U_MATRIX);
        aColorLocation = GLES20.glGetAttribLocation(programId, A_COLOR);
        aPositionLocation = GLES20.glGetAttribLocation(programId, A_POSITION);
        uColorLocation = GLES20.glGetUniformLocation(programId, U_COLOR);
    }

    public void setUniforms(float[] matrix, int color) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
        ColorHelper.setColor(uColorLocation, color);
    }
}
