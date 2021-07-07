package org.app.opengl_es_android_version.object.object2d;

//public interface Object2D {
//
//    float[] modelMatrix = new float[16];
//
//    /**
//     * 这个类中绑定纹理
//     */
//    void bindData(Context context);
//
//    /***
//     * 绘制
//     */
//    void draw();
//}

import android.content.Context;
import android.opengl.Matrix;

import org.app.opengl_es_android_version.program.MyColorShaderProgram;
import org.app.opengl_es_android_version.program.TextureShaderProgram;

public abstract class Object2D {

    public Context context;

    public float[] modelMatrix = new float[16];
    public float[] mvpMatrix = new float[16];

    protected TextureShaderProgram textureShaderProgram;
    protected MyColorShaderProgram colorShaderProgram;

    boolean isBind;

    public Object2D() {
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.setIdentityM(mvpMatrix, 0);
    }

    /**
     * 这个类中绑定纹理
     */
    @Deprecated
    public void bindData(Context context) {

    }

    public abstract void unbind();

    /***
     * 绘制
     */
    public abstract void draw();

    /***
     * 加入了视图投影矩阵的绘制方法
     * @param viewProjectMatrix
     */
    public void draw(float[] viewProjectMatrix) {
        if(!isBind){
            bindData(context);
            isBind = true;
        }
        Matrix.multiplyMM(mvpMatrix, 0, viewProjectMatrix, 0, modelMatrix, 0);
        draw();
        unbind();
    }

    public void setTextureShaderProgram(TextureShaderProgram shaderProgram) {
        this.textureShaderProgram = shaderProgram;
    }

    public Object2D setColorShaderProgram(MyColorShaderProgram colorShaderProgram) {
        this.colorShaderProgram = colorShaderProgram;
        this.context = colorShaderProgram.getContext();

        return this;
    }
}
