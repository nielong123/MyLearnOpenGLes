package org.app.opengl_es_android_version.util;

import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

public class ErrorUtils {

    private final static String TAG = ErrorUtils.class.getSimpleName();

    static public void getError() {
        int errorCode;
        while ((errorCode = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            String errStr = GLUtils.getEGLErrorString(errorCode);
            Log.e(TAG, "getError: " + errStr);
        }
    }
}
