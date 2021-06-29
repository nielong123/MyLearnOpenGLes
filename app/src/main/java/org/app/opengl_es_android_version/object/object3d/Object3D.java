package org.app.opengl_es_android_version.object.object3d;

import android.content.Context;
import android.opengl.Matrix;

import org.app.opengl_es_android_version.data.VertexArray;

public abstract class Object3D {

    protected Context context;

    protected int programId;
    protected int aMatrixLocation;
    protected int uColorLocation;
    protected int aPositionLocation;
    protected int aTextureCoord;

    public float[] modelMatrix = new float[16];

    public float[] mvpMatrix = new float[16];

    protected boolean isBind = false;


    protected VertexArray vertexArray;

    public Object3D(Context context) {
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
    }
}
