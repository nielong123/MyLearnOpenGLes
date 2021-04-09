package org.app.opengl_es_android_version.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import org.app.opengl_es_android_version.object.object2d.Object2D;
import org.app.opengl_es_android_version.object.object2d.Rectangle1;
import org.app.opengl_es_android_version.object.object2d.RectangleWithTexture;
import org.app.opengl_es_android_version.object.object2d.Star5P;
import org.app.opengl_es_android_version.object.object2d.demo.CoordinateLines;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class My2DRenderer_1 implements GLSurfaceView.Renderer {

    private final Context context;

    List<Object2D> drawObjectList = new ArrayList<>();

    //视角矩阵
    private final float[] viewMatrix = new float[16];
    //投影矩阵
    private final float[] projectMatrix = new float[16];

    //
    private final float[] mvpMatrix = new float[16];

    public My2DRenderer_1(Context context) {
        this.context = context;
        Matrix.setIdentityM(viewMatrix, 0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        drawObjectList.add(new CoordinateLines());
//        drawObjectList.add(new Rectangle());
//        drawObjectList.add(new Circle());
//        drawObjectList.add(new Polyline());
//        drawObjectList.add(new Star5P());
        drawObjectList.add(new RectangleWithTexture(context));
//        drawObjectList.add(new Rectangle1(context));
//        drawObjectList.add(new Triangle());
//        drawObjectList.add(new Square());

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        for (Object2D object2D : drawObjectList) {
            object2D.bindData(context);
            object2D.draw();
        }
    }
}
