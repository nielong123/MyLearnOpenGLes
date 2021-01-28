package org.app.opengl_es_android_version.program;

import android.content.Context;
import android.opengl.GLES20;

import org.app.opengl_es_android_version.util.ShaderHelper;
import org.app.opengl_es_android_version.util.TextResourceReader;

public class ShaderProgram {

    protected int programId;

    public ShaderProgram(String vertexShaderResourceStr,
                         String fragmentShaderResourceStr) {
        programId = ShaderHelper.buildProgram(vertexShaderResourceStr,
                fragmentShaderResourceStr);
    }

    public ShaderProgram(Context context, int vertexShaderResourceId,
                         int fragmentShaderResourceId) {
        programId = ShaderHelper.buildProgram(
                TextResourceReader.readTextFileFromResource(context, vertexShaderResourceId),
                TextResourceReader.readTextFileFromResource(context, fragmentShaderResourceId)
        );
    }

    public void userProgram() {
        GLES20.glUseProgram(programId);
    }

    public int getShaderProgramId() {
        return programId;
    }

    public void delProgram() {
        GLES20.glDeleteProgram(programId);
    }
}
