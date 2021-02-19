package org.app.opengl_es_android_version.object;

import org.app.opengl_es_android_version.program.ColorShaderProgram;
import org.app.opengl_es_android_version.program.TextureShaderProgram;

public interface Object {

    void bindData(TextureShaderProgram shaderProgram);

    void bindData(ColorShaderProgram colorShaderProgram);

    void draw();

}
