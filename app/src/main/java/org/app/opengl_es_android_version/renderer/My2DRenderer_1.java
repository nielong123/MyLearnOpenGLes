package org.app.opengl_es_android_version.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import org.app.opengl_es_android_version.object.object2d.Object2D;
import org.app.opengl_es_android_version.object.object2d.Star5P;
import org.app.opengl_es_android_version.object.object2d.TestTable2D;
import org.app.opengl_es_android_version.util.Geometry;
import org.app.opengl_es_android_version.util.VaryTools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;

public class My2DRenderer_1 implements GLSurfaceView.Renderer {

    final String TAG = My2DRenderer_1.class.getSimpleName();

    private final Context context;

    VaryTools varyTools;

    List<Object2D> drawObjectList = new CopyOnWriteArrayList<>();

    Object2D object2D;


    public My2DRenderer_1(Context context) {
        this.context = context;
        varyTools = new VaryTools();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear(GL_COLOR_BUFFER_BIT);

//        rectangle = new Rect(context,
//                new Geometry.Rect(new Geometry.Point(0, 0, 0), 0.75f, 0.75f));

//        TestTable2D testTable2D = new TestTable2D(context);
//        for (Geometry.Rect rect : cutScreen()) {
//            drawObjectList.add(new TestTable2D(context, rect));
//        }
        drawObjectList.add(new TestTable2D(context));
//        List<Rect> rectList = new ArrayList<>();
//        rectList.add(new Rect(0, 1, 1, 0));
//        rectList.add(new Rect(-1, 1, 0, 0));
//        drawObjectList.add(new MultipleTestTable2D(context, rectList));

//        drawObjectList.add(new CoordinateLines(context));
//        drawObjectList.add(new Circle(context));
//        drawObjectList.add(new Polyline(context));
//        drawObjectList.add(new Rectangle1(context));
//        drawObjectList.add(new RectangleWithTexture(context));
//        drawObjectList.add(new Star5P(context));

//        drawObjectList.add(rectangle);
//        drawObjectList.add(testTable2D1);
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
//        viewMatrix = new float[]{1, 0.2f, 0, 0,
//                0, 1, 0, 0,
//                0, 0, 1, 0,
//                0, 0, 0, 1};
//        Matrix.multiplyMM(viewProjectMatrix, 0, projectMatrix, 0, viewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GL_COLOR_BUFFER_BIT);

        for (Object2D object2D : drawObjectList) {
            object2D.draw(varyTools.getViewProjectionMatrix());
        }
    }

    public void handleTouchDown(float normalizedX, float normalizedY) {
    }

    public void handleTouchMove(float normalizedX, float normalizedY) {
        varyTools.translate(normalizedX / 10, normalizedY / 10, 0);
//        Log.e(TAG, "handleTouchMove: normalizedX = " + normalizedX + " normalizedY = " + normalizedY);
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

    public void resetMatrix() {
        varyTools.resetMatrix();
    }

    private List<Geometry.Rect> cutScreen() {
        final int slice = 3;
        final float width = 2;
        final float height = 2;
        List<Geometry.Rect> rectList = new ArrayList<>();
        for (int i = 1; i < slice + 1; i++) {
            float top = height * i / slice - height / 2;
            float bottom = height * (i - 1) / slice - height / 2;
            for (int j = 1; j < slice + 1; j++) {
                float left = width * i / slice - width / 2;
                float right = width * (i - 1) / slice - width / 2;
                rectList.add(new Geometry.Rect(left, top, right, bottom));
            }
        }

        return rectList;
    }

}
