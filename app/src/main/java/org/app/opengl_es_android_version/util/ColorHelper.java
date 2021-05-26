package org.app.opengl_es_android_version.util;

import android.graphics.Color;
import android.opengl.GLES20;

public class ColorHelper {

    public static void setColor(int colorLocation, int colorRes) {
        float r = ((float) Color.red(colorRes)) / 0xff;
        float g = ((float) Color.green(colorRes)) / 0xff;
        float b = ((float) Color.blue(colorRes)) / 0xff;
        float a = ((float) Color.alpha(colorRes)) / 0xff;
        GLES20.glUniform4f(colorLocation, r, g, b, a);
    }
}
