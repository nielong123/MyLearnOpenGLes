package org.app.opengl_es_android_version.program;

import android.content.Context;
import android.opengl.GLES20;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.contant.Constants;
import org.app.opengl_es_android_version.util.ColorHelper;

final public class MyColorShaderProgram extends ShaderProgram {

    public final int aPositionLocation;
    public final int aMatrixLocation;
    public final int aColorLocation;

    private Context context;

    public MyColorShaderProgram(Context context) {
        super(context, R.raw.texture_vertex_shader_copy, R.raw.simple_fragment_shader1_5);
        this.context = context;
        GLES20.glUseProgram(programId);
        aMatrixLocation = GLES20.glGetUniformLocation(programId, Constants.U_MATRIX);
        aPositionLocation = GLES20.glGetAttribLocation(programId, Constants.A_POSITION);
        aColorLocation = GLES20.glGetUniformLocation(programId, Constants.A_COLOR);
    }


    public Context getContext() {
        return context;
    }
}
