package org.app.opengl_es_android_version.object;

import org.app.opengl_es_android_version.program.ColorShaderProgram;
import org.app.opengl_es_android_version.program.TextureShaderProgram;

public abstract class Object3D {

    public abstract void bindData(TextureShaderProgram shaderProgram);

    public abstract void bindData(ColorShaderProgram colorShaderProgram);

    public abstract void draw();

}
