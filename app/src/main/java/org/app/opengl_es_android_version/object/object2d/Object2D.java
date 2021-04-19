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

public abstract class Object2D {

    float[] modelMatrix = new float[16];

    /**
     * 这个类中绑定纹理
     */
    public abstract void bindData(Context context);

    /***
     * 绘制
     */
    public abstract void draw();
}
