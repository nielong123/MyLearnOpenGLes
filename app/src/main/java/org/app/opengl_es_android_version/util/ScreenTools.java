package org.app.opengl_es_android_version.util;

import android.view.View;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class ScreenTools {

    /**
     * 屏幕坐标系点转OpenGL坐标系
     *
     * @return
     */
    public static float toOpenGLCoord(View view, float value, boolean isWidth) {
        if (isWidth) {
            return (value / (float) view.getWidth()) * 2 - 1;
        } else {
            return -((value / (float) view.getHeight()) * 2 - 1);
        }
    }

    /**
     * 计算两个点之间的距离
     *
     * @param x1
     * @param x2
     * @param y1
     * @param y2
     * @return
     */
    public static double computeDis(float x1, float x2, float y1, float y2) {
        return sqrt(pow((x2 - x1), 2) + pow((y2 - y1), 2));
    }

}
