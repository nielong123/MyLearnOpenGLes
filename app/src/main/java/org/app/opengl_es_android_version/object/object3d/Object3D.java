package org.app.opengl_es_android_version.object.object3d;

import android.content.Context;
import android.opengl.Matrix;

import org.app.opengl_es_android_version.data.VertexArray;
import org.app.opengl_es_android_version.program.MyColorShaderProgram;

public abstract class Object3D {

    protected Context context;

    public float[] modelMatrix = new float[16];

    public float[] mvpMatrix = new float[16];

    protected MyColorShaderProgram colorShaderProgram;

    protected VertexArray vertexArray;

    public Object3D() {
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.setIdentityM(mvpMatrix, 0);
    }

    /**
     * 这个类中绑定纹理
     */
    public void bindData(Context context) {
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

        bindData(context);
        Matrix.multiplyMM(mvpMatrix, 0, viewProjectMatrix, 0, modelMatrix, 0);
        draw();
        unbind();
    }

    public Object3D setColorShaderProgram(MyColorShaderProgram colorShaderProgram) {
        this.colorShaderProgram = colorShaderProgram;
        this.context = colorShaderProgram.getContext();
        return this;
    }
}
