package org.app.opengl_es_android_version.object.object2d;

import android.content.Context;

public interface Object2D {

    float[] modelMatrix = new float[16];

    /**
     * 这个类中绑定纹理
     */
    void bindData(Context context);

    /***
     * 绘制
     */
    void draw();
}
