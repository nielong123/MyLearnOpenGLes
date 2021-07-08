package org.app.opengl_es_android_version.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.util.Log;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.activity.My3DActivity;
import org.app.opengl_es_android_version.object.object3d.CoordinateLines3D;
import org.app.opengl_es_android_version.object.object3d.Object3D;
import org.app.opengl_es_android_version.object.object3d.Planet;
import org.app.opengl_es_android_version.program.MyColorShaderProgram;
import org.app.opengl_es_android_version.util.Geometry;
import org.app.opengl_es_android_version.util.VaryTools;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class My3DRenderer_1 implements GLSurfaceView.Renderer {

    final String TAG = My3DRenderer_1.class.getSimpleName();

    private final Context context;

    private int width, height;

    MyColorShaderProgram colorShaderProgram;

    List<Object3D> drawObjectList = new ArrayList<>();

    VaryTools varyTools;

    Planet earth, moon;

    final long earthRotateSpeed = 50l;

    Geometry.Point viewCenterPoint = new Geometry.Point(0.5f, 0.5f, 0.5f);

    public My3DRenderer_1(Context context) {
        this.context = context;
        varyTools = new VaryTools();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        colorShaderProgram = new MyColorShaderProgram(context);

        earth = new Planet(viewCenterPoint.x,
                viewCenterPoint.y,
                viewCenterPoint.z,
                0.5f);
        earth.setColorShaderProgram(colorShaderProgram);
        moon = new Planet(
                viewCenterPoint.x + 1f,
                viewCenterPoint.x + 1f,
                viewCenterPoint.z + 1.5f,
                0.3f,
                context.getColor(R.color.colorPrimaryDark));
        moon.setColorShaderProgram(colorShaderProgram);

        drawObjectList.add(new CoordinateLines3D().setColorShaderProgram(colorShaderProgram));
//        drawObjectList.add(new TestFbo3D(context));
        drawObjectList.add(earth);
        drawObjectList.add(moon);
//        drawObjectList.add(new TestTable3D(context).setColorShaderProgram(colorShaderProgram));
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
        GLES20.glViewport((int) viewCenterPoint.x, (int) viewCenterPoint.y, width, height);
        varyTools.setCamera(4f, 4f, 4f,
                viewCenterPoint.x, viewCenterPoint.y, viewCenterPoint.z,
                0f, 1f, 0f);
//        GLES20.glViewport(0, 0, width, height);
//        varyTools.setCamera(4f, 4f, 4f,
//                0f, 0f, 0f,
//                0f, 1f, 0f);
        varyTools.setProjection(width, height);
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

    public void startMoonRotating() {
        handler.postDelayed(runnable, earthRotateSpeed);
    }

    public void stopMoonRotating() {
        handler.removeCallbacks(runnable);
    }

    public void getMoonCoordinate() {
        Geometry.Point coordinate = moon.getCoordinate();
        Log.e(TAG, "getMoonCoordinate: x = " + coordinate.x +
                "  y = " + coordinate.y + "  z = " + coordinate.z);
    }

    Handler handler = new Handler();

    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            Geometry.Point earthCenter = earth.getCenterPoint();
            moon.startRotating(earthCenter.x, 0, earthCenter.z);
            handler.postDelayed(this, earthRotateSpeed);
            ((My3DActivity) context).handler.sendEmptyMessage(0);
        }
    };

}
