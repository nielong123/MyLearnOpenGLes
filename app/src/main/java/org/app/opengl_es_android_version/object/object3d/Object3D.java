package org.app.opengl_es_android_version.object.object3d;

import android.content.Context;
import android.opengl.Matrix;

import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.program.MyColorShaderProgram;
import org.app.opengl_es_android_version.program.TextureShaderProgram;
import org.app.opengl_es_android_version.util.ErrorUtils;

public abstract class Object3D {

    protected Context context;

    public float[] modelMatrix = new float[16];

    public float[] mvpMatrix = new float[16];

    protected MyColorShaderProgram colorShaderProgram;
    protected TextureShaderProgram textureShaderProgram;

    protected VertexArray vertexArray;

    private boolean isBind;

    public Object3D() {
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.setIdentityM(mvpMatrix, 0);
    }

    /**
     * 这个类中绑定纹理
     */
    @Deprecated
    public void bindData(Context context) {
        isBind = true;
    }

    public abstract void unbind();

    /***
     * 绘制
     */
    protected abstract void draw();

    /***
     * 加入了视图投影矩阵的绘制方法
     * @param viewProjectMatrix
     */
    public void draw(final float[] viewProjectMatrix) {
        if (!isBind) {
            bindData(context);
        }
        Matrix.multiplyMM(mvpMatrix, 0, viewProjectMatrix, 0, modelMatrix, 0);
        draw();
        unbind();
        ErrorUtils.getError();
    }

    public Object3D setColorShaderProgram(MyColorShaderProgram colorShaderProgram) {
        this.colorShaderProgram = colorShaderProgram;
        this.context = colorShaderProgram.getContext();
        return this;
    }

    public Object3D setTextureShaderProgram(TextureShaderProgram textureShaderProgram) {
        this.textureShaderProgram = textureShaderProgram;
        return this;
    }
}
