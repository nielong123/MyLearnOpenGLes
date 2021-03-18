package org.app.opengl_es_android_version.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import org.app.opengl_es_android_version.object.object2d.Object2D;
import org.app.opengl_es_android_version.object.object2d.Rectangle;
import org.app.opengl_es_android_version.object.object2d.demo.Triangle;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class My2DRenderer_1 implements GLSurfaceView.Renderer {

    private final Context context;

    List<Object2D> drawObjectList = new ArrayList<>();

    public My2DRenderer_1(Context context) {
        this.context = context;

        drawObjectList.add(new Rectangle());
//        drawObjectList.add(new Circle());
//        drawObjectList.add(new Polyline());
        drawObjectList.add(new Triangle());
//        drawObjectList.add(new Square());
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        for (Object2D object : drawObjectList) {
            object.bindData(context);
            object.draw();
        }
    }
}
