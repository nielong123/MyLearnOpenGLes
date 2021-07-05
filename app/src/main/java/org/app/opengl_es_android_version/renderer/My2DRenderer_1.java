package org.app.opengl_es_android_version.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.object.object2d.MultipleTestTable2D;
import org.app.opengl_es_android_version.object.object2d.Object2D;
import org.app.opengl_es_android_version.object.object2d.Star5P;
import org.app.opengl_es_android_version.program.TextureShaderProgram;
import org.app.opengl_es_android_version.util.Geometry;
import org.app.opengl_es_android_version.util.TextureHelper;
import org.app.opengl_es_android_version.util.VaryTools;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;

public class My2DRenderer_1 implements GLSurfaceView.Renderer {

    final String TAG = My2DRenderer_1.class.getSimpleName();

    private final Context context;

    VaryTools varyTools;

//    List<Object2D> drawObjectList = new CopyOnWriteArrayList<>();

    Object2D object2D;

    private static final float vf = 0.5f;
    private static final float vf1 = 0.2f;


    public My2DRenderer_1(Context context) {
        this.context = context;
        varyTools = new VaryTools();
    }

    List<Geometry.Rect> rects = new ArrayList<>();
    int[] textureIds = new int[]{};
    TextureShaderProgram shaderProgram;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear(GL_COLOR_BUFFER_BIT);

        rects = cutScreen();
        shaderProgram = new TextureShaderProgram(context);
//        textureIds = TextureHelper.loadMultipleTexture(context, R.drawable.jsy, rects.size());
        Log.e(TAG, "onSurfaceCreated: ");

//        TestTable2D testTable2D = new TestTable2D(context);
//        List<Geometry.Rect> rects = cutScreen();
//        for (Geometry.Rect rect : rects) {
//            drawObjectList.add(new TestTable2D(context, rect));
//        }
//        drawObjectList.add(new MultipleTestTable2D(context));
//        drawObjectList.add(new TestTable2D(context)); //todo
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
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        if (textureIds != null) {
            GLES20.glDeleteTextures(textureIds.length, textureIds, 0);
        }
        textureIds = new int[]{};
        textureIds = TextureHelper.loadMultipleTexture(context, R.drawable.jsy, rects.size());
        for (int i = 0; i < rects.size(); i++) {
            Object2D object2D = new MultipleTestTable2D(context, textureIds[i], rects.get(i));
            object2D.setTextureShaderProgram(shaderProgram);
            object2D.draw(varyTools.getViewProjectionMatrix());
        }
    }

    public void handleTouchDown(float normalizedX, float normalizedY) {
    }

    public void handleTouchMove(float normalizedX, float normalizedY, int action) {
        varyTools.translate(normalizedX / 10, normalizedY / 10, 0);
    }

    public void add2DObject() {
        if (object2D == null) {
            object2D = new Star5P(context);
//            drawObjectList.add(object2D);
        } else {
//            drawObjectList.remove(object2D);
            object2D = null;
        }
    }

    public void resetMatrix() {
        varyTools.resetMatrix();
    }

    private List<Geometry.Rect> cutScreen() {
        final int slice = 5;
        final float width = 3;
        final float height = 3;
        List<Geometry.Rect> rectList = new ArrayList<>();
        for (int i = 1; i < slice + 1; i++) {
            float top = height * i / slice - height / 2;
            float bottom = height * (i - 1) / slice - height / 2;
            for (int j = 1; j < slice + 1; j++) {
                float left = width * j / slice - width / 2;
                float right = width * (j - 1) / slice - width / 2;
                rectList.add(new Geometry.Rect(left, top, right, bottom));
            }
        }

        return rectList;
    }

}
