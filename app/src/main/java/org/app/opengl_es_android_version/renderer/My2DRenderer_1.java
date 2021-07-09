package org.app.opengl_es_android_version.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import org.app.opengl_es_android_version.object.object2d.Circle;
import org.app.opengl_es_android_version.object.object2d.Object2D;
import org.app.opengl_es_android_version.object.object2d.Polyline;
import org.app.opengl_es_android_version.object.object2d.Rectangle;
import org.app.opengl_es_android_version.object.object2d.Rectangle1;
import org.app.opengl_es_android_version.object.object2d.Star5P;
import org.app.opengl_es_android_version.object.object2d.demo.CoordinateLines;
import org.app.opengl_es_android_version.object.object2d.demo.Triangle;
import org.app.opengl_es_android_version.program.MyColorShaderProgram;
import org.app.opengl_es_android_version.program.TextureShaderProgram;
import org.app.opengl_es_android_version.util.Geometry;
import org.app.opengl_es_android_version.util.VaryTools;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_TEXTURE_2D;

public class My2DRenderer_1 implements GLSurfaceView.Renderer {

    final String TAG = My2DRenderer_1.class.getSimpleName();

    private final Context context;

    VaryTools varyTools;

    List<Object2D> drawObjectList = new CopyOnWriteArrayList<>();

    Object2D object2D;

    private ByteBuffer mBuffer;

    private Callback mCallback;

    int width, height;

    List<Geometry.Rect> rects = new ArrayList<>();
    int[] textureIds = new int[]{};
    TextureShaderProgram textureShaderProgram;
    MyColorShaderProgram colorShaderProgram;
    private int[] renderBuffer = new int[1];

    private boolean isMirror = false;

    public My2DRenderer_1(Context context) {
        this.context = context;
        varyTools = new VaryTools();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear(GL_COLOR_BUFFER_BIT);

        rects = cutScreen();
        textureShaderProgram = new TextureShaderProgram(context);
        colorShaderProgram = new MyColorShaderProgram(context);

//        textureIds = TextureHelper.loadMultipleTexture(context, R.drawable.jsy, rects.size());
//        Log.e(TAG, "onSurfaceCreated: ");

//        List<Rect> rectList = new ArrayList<>();
//        rectList.add(new Geometry.Rect(0, 1, 1, 0));
//        rectList.add(new Rect(-1, 1, 0, 0));
//        drawObjectList.add(new MultipleTestTable2D(context, rectList));

        drawObjectList.add(new CoordinateLines(context).setColorShaderProgram(colorShaderProgram));
        drawObjectList.add(new Triangle().setColorShaderProgram(colorShaderProgram));
        drawObjectList.add(new Circle().setColorShaderProgram(colorShaderProgram));
        drawObjectList.add(new Polyline().setColorShaderProgram(colorShaderProgram));
        drawObjectList.add(new Rectangle1().setColorShaderProgram(colorShaderProgram));
        drawObjectList.add(new Star5P().setColorShaderProgram(colorShaderProgram));

        drawObjectList.add(new Rectangle().setColorShaderProgram(colorShaderProgram));

//        TestTable2D testTable2D = new TestTable2D();
//        testTable2D.setTextureShaderProgram(textureShaderProgram);
//        testTable2D.setTextureId(TextureHelper.loadTexture(context, R.drawable.jsy));
//        drawObjectList.add(testTable2D);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
        GLES20.glViewport(0, 0, width, height);
    }

    int[] fboId = {0};
    int[] fboTextureId = {0, 0};

    @Override
    public void onDrawFrame(GL10 gl) {

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        initFbo();
        bindFbo();

        for (Object2D object2D : drawObjectList) {
            object2D.draw(varyTools.getViewProjectionMatrix());
        }
        if (mCallback != null && isMirror) {
            isMirror = false;
            GLES20.glReadPixels(0, 0, width, height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, mBuffer);
            mCallback.onReadPixData(mBuffer, width, height);
        }
        clearFbo();

//        if (textureIds != null) {
//            GLES20.glDeleteTextures(textureIds.length, textureIds, 0);
//        }
//        textureIds = new int[]{};
//        textureIds = TextureHelper.loadMultipleTexture(context, R.drawable.jsy, rects.size());
//        for (int i = 0; i < rects.size(); i++) {
//            Object2D object2D = new MultipleTestTable2D(textureIds[i], rects.get(i));
//            object2D.setTextureShaderProgram(textureShaderProgram);
//            object2D.draw(varyTools.getViewProjectionMatrix());
//        }
    }


    private boolean initFbo() {
        GLES20.glGenFramebuffers(1, fboId, 0);
        GLES20.glGenRenderbuffers(1, renderBuffer, 0);

        GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, renderBuffer[0]);
        GLES20.glRenderbufferStorage(GLES20.GL_RENDERBUFFER,
                GLES20.GL_DEPTH_COMPONENT16, width, height);
        GLES20.glFramebufferRenderbuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT,
                GLES20.GL_RENDERBUFFER, renderBuffer[0]);
        GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, 0);
        // 创建并初始化 FBO 纹理
        GLES20.glGenTextures(2, fboTextureId, 0);
        for (int i = 0; i < 2; i++) {
            GLES20.glBindTexture(GL_TEXTURE_2D, fboTextureId[i]);
//            if (i == 0) {
//                GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, mBitmap, 0);
//            } else {
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height,
                    0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
//            }
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

            GLES20.glBindTexture(GL_TEXTURE_2D, GLES20.GL_NONE);
        }

        mBuffer = ByteBuffer.allocate(width * height * 4);
        return true;
    }

    private void bindFbo() {
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, renderBuffer[0]);
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
                GLES20.GL_TEXTURE_2D, fboTextureId[1], 0);
        GLES20.glFramebufferRenderbuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT,
                GLES20.GL_RENDERBUFFER, renderBuffer[0]);
        // 解绑纹理
//        GLES20.glBindTexture(GL_TEXTURE_2D, GLES20.GL_NONE);
        // 解绑 FBO
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_NONE);
    }

    private void clearFbo() {
        GLES20.glDeleteTextures(2, fboTextureId, 0);
        GLES20.glDeleteRenderbuffers(1, renderBuffer, 0);
        GLES20.glDeleteFramebuffers(1, fboId, 0);
    }

    public void handleTouchDown(float normalizedX, float normalizedY) {
    }

    public void handleTouchMove(float normalizedX, float normalizedY, int action) {
        varyTools.translate(normalizedX / 10, normalizedY / 10, 0);
    }

    public void add2DObject() {
        if (object2D == null) {
//            object2D = new Star5P(context);
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
        final int slice = 4;
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

    public void setMirror(boolean mirror) {
        isMirror = mirror;
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public interface Callback {

        void onReadPixData(ByteBuffer data, int width, int height);

    }

}
