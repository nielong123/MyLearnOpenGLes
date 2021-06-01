package org.app.opengl_es_android_version.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import org.app.opengl_es_android_version.object.object2d.Circle;
import org.app.opengl_es_android_version.object.object2d.Object2D;
import org.app.opengl_es_android_version.object.object2d.Polyline;
import org.app.opengl_es_android_version.object.object2d.Rectangle;
import org.app.opengl_es_android_version.object.object2d.Rectangle1;
import org.app.opengl_es_android_version.object.object2d.RectangleWithTexture;
import org.app.opengl_es_android_version.object.object2d.Star5P;
import org.app.opengl_es_android_version.object.object2d.demo.CoordinateLines;
import org.app.opengl_es_android_version.object.object2d.demo.Square;
import org.app.opengl_es_android_version.object.object2d.demo.Triangle;
import org.app.opengl_es_android_version.object.object3d.TestTable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;

public class My2DRenderer_1 implements GLSurfaceView.Renderer {

    private final Context context;

    List<Object2D> drawObjectList = new CopyOnWriteArrayList<>();

    //视角矩阵
    private float[] viewMatrix = new float[16];
    //投影矩阵
    private final float[] projectMatrix = new float[16];
    //视角与投影的乘积矩阵
    private final float[] viewProjectMatrix = new float[16];


    Object2D object2D;


    public My2DRenderer_1(Context context) {
        this.context = context;
        Matrix.setIdentityM(viewMatrix, 0);
        Matrix.setIdentityM(projectMatrix, 0);
        Matrix.setIdentityM(viewProjectMatrix, 0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear(GL_COLOR_BUFFER_BIT);
//        GLES20.glEnable(GLES20.GL_TEXTURE_2D);

        drawObjectList.add(new CoordinateLines(context));
        drawObjectList.add(new Rectangle(context));
//        drawObjectList.add(new Circle(context));
//        drawObjectList.add(new Polyline(context));
//        drawObjectList.add(new Rectangle1(context));
//        drawObjectList.add(new RectangleWithTexture(context));
//        drawObjectList.add(new Star5P(context));
        drawObjectList.add(new TestTable(context));
//        drawObjectList.add(new Triangle(context));
//        drawObjectList.add(new Square(context));
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
//        MatrixHelper.perspectiveM(projectMatrix, 45, (float) width / (float) height, 1f, 100f);
//        Matrix.setLookAtM(viewMatrix, 0,
//                4f, 4f, 4f,
//                0f, 0f, 0f,
//                0f, 1f, 0f);
        viewMatrix = new float[]{1, 0.2f, 0, 0,
                                0, 1, 0, 0,
                                0, 0, 1, 0,
                                0, 0, 0, 1};
        Matrix.multiplyMM(viewProjectMatrix, 0, projectMatrix, 0, viewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GL_COLOR_BUFFER_BIT);

        for (Object2D object2D : drawObjectList) {
            object2D.draw(viewProjectMatrix);
        }
    }

    public void handleTouchDown(float normalizedX, float normalizedY) {
    }

    public void handleTouchMove(float normalizedX, float normalizedY) {
    }

    public void add2DObject() {
        if (object2D == null) {
            object2D = new Star5P(context);
            drawObjectList.add(object2D);
        } else {
            drawObjectList.remove(object2D);
            object2D = null;
        }
    }

}
