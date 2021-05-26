package org.app.opengl_es_android_version.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import org.app.opengl_es_android_version.object.object3d.CoordinateLines3D;
import org.app.opengl_es_android_version.object.object3d.Object3D;
import org.app.opengl_es_android_version.object.object3d.Planet;
import org.app.opengl_es_android_version.object.object3d.Rectangle3D;
import org.app.opengl_es_android_version.util.VaryTools;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class My3DRenderer_1 implements GLSurfaceView.Renderer {

    final String TAG = My3DRenderer_1.class.getSimpleName();

    private final Context context;

    private int width, height;

    List<Object3D> drawObjectList = new ArrayList<>();

    VaryTools varyTools;

    Planet earth, moon;

    public My3DRenderer_1(Context context) {
        this.context = context;
        varyTools = new VaryTools();
        earth = new Planet(context);
        moon = new Planet(context, 1f, 1f, 1.5f, 0.3f);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
//        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        drawObjectList.add(new CoordinateLines3D(context));
        drawObjectList.add(new Rectangle3D(context));
        drawObjectList.add(earth);
        drawObjectList.add(moon);
//        drawObjectList.add(new Polyline());
//        drawObjectList.add(new Star5P());
//        drawObjectList.add(new RectangleWithTexture(context));
//        drawObjectList.add(new Rectangle1(context));
//        drawObjectList.add(new Triangle());
//        drawObjectList.add(new Square());
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
        GLES20.glViewport(0, 0, width, height);
        varyTools.setProjection(width, height);
        varyTools.setCamera(4f, 4f, 4f,
                0f, 0f, 0f,
                0f, 1f, 0f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        for (Object3D object3D : drawObjectList) {
            object3D.draw(varyTools.getViewProjectionMatrix());
        }
    }

    /**
     * 旋转
     *
     * @param dx
     * @param dy
     */
    public void rotate(float dx, float dy) {
        //根据当前缩放的比例调节平移参数
        if (dx == 0 || dy == 0) {
            return;
        }
        varyTools.rotate(1f, 0, dx, 0);
    }

    /**
     * 缩放视图
     *
     * @param scale 缩放比例
     */
    public void zoom(float scale) {
        varyTools.scale(scale, scale, scale);
    }


    public void resetViewProjection() {
        varyTools.setProjection(width, height);
        varyTools.resetMatrix();
    }
}
