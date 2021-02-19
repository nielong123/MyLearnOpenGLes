package org.app.opengl_es_android_version.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class PanoramaRenderer implements GLSurfaceView.Renderer {

    private final Context context;
    private final float[] modelViewProjectMatrix = new float[16];
    private final float[] viewProjectMatrix = new float[16];
    private final float[] projectMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    public PanoramaRenderer(Context context) {
        this.context = context;

        Matrix.setIdentityM(projectMatrix, 0);
        Matrix.setIdentityM(viewMatrix, 0);
        Matrix.setIdentityM(viewProjectMatrix, 0);
        Matrix.setIdentityM(modelViewProjectMatrix, 0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0, 0, 0, 0);



    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
