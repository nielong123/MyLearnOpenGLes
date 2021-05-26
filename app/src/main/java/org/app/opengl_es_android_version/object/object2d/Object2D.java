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
import android.widget.Toast;

public abstract class Object2D {

    public Context context;

    private boolean isBind = false;

    public float[] modelMatrix = new float[16];

    public float[] mvpMatrix = new float[16];

    public Object2D(Context context) {
        this.context = context;
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.setIdentityM(mvpMatrix, 0);
    }

    /**
     * 这个类中绑定纹理
     */
    public void bindData(Context context) {
        isBind = true;
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
        if (!isBind) {
            bindData(context);
        }
        Matrix.multiplyMM(mvpMatrix, 0, viewProjectMatrix, 0, modelMatrix, 0);
        draw();
        unbind();
    }
}
