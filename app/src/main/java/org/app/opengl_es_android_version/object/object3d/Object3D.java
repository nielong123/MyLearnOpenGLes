package org.app.opengl_es_android_version.object.object3d;

import android.content.Context;
import android.opengl.Matrix;

public abstract class Object3D {

    public Object3D() {
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.setIdentityM(mvpMatrix, 0);
    }

    public float[] modelMatrix = new float[16];

    public float[] mvpMatrix = new float[16];

    /**
     * 这个类中绑定纹理
     */
    public abstract void bindData(Context context);

    /***
     * 绘制
     */
    public abstract void draw();

    /***
     * 加入了视图投影矩阵的绘制方法
     * @param viewProjectMatrix
     */
    public abstract void draw(float[] viewProjectMatrix);
}
