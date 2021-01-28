package org.app.opengl_es_android_version;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import org.app.opengl_es_android_version.object.Mallet;
import org.app.opengl_es_android_version.object.Puck;
import org.app.opengl_es_android_version.object.Table;
import org.app.opengl_es_android_version.program.ColorShaderProgram;
import org.app.opengl_es_android_version.program.TextureShaderProgram;
import org.app.opengl_es_android_version.util.Geometry;
import org.app.opengl_es_android_version.util.MatrixHelper;
import org.app.opengl_es_android_version.util.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class HockeyRenderer3 implements GLSurfaceView.Renderer {

    String TAG = this.getClass().getSimpleName();

    Context context;


    private Table table;
    private Mallet mallet;
    private Puck puck;
    private TextureShaderProgram textureShaderProgram;
    private ColorShaderProgram colorShaderProgram;

    int textureId;

    private final float[] modelViewProjectionMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    //倒置视图投影矩阵
    private final float[] invertViewProjectionMatrix = new float[16];


    public HockeyRenderer3(Context context) {
        this.context = context;
        Matrix.setIdentityM(projectionMatrix, 0);
        Matrix.setIdentityM(viewMatrix, 0);
        Matrix.setIdentityM(viewProjectionMatrix, 0);
        Matrix.setIdentityM(modelViewProjectionMatrix, 0);
        Matrix.setIdentityM(invertViewProjectionMatrix, 0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        table = new Table();
        mallet = new Mallet(0.08f, 0.15f, 32);
        puck = new Puck(0.03f, 0.01f, 32);

        textureShaderProgram = new TextureShaderProgram(context);
        colorShaderProgram = new ColorShaderProgram(context);

        textureId = TextureHelper.loadTexture(context, R.mipmap.start);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width / (float) height, 1f, 100f);
        Matrix.setLookAtM(viewMatrix, 0,
                0f, 1.2f, 2.2f, //eye
                0f, 0f, 0f, //center
                0f, 1f, 0f);    //up

        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        Matrix.invertM(invertViewProjectionMatrix, 0, viewProjectionMatrix, 0);

        Matrix.rotateM(table.modelMatrix, 0, -90f, 1f, 0f, 0f);
        Matrix.translateM(mallet.modelMatrix, 0, 0f, mallet.height / 2f, 0.5f);
        Matrix.translateM(puck.modelMatrix, 0, 0f, puck.height / 2f, 0f);

        mallet.position = new Geometry.Point(0f, mallet.height / 2f, 0.5f);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        Matrix.multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 0, table.modelMatrix, 0);
        textureShaderProgram.userProgram();
        textureShaderProgram.setUniforms(modelViewProjectionMatrix, textureId);
        table.bindData(textureShaderProgram);
        table.draw();

        Matrix.setIdentityM(mallet.modelMatrix, 0);
        Matrix.translateM(mallet.modelMatrix, 0, mallet.position.x, mallet.position.y, mallet.position.z);
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 0, mallet.modelMatrix, 0);
        colorShaderProgram.userProgram();
        colorShaderProgram.setUniforms(modelViewProjectionMatrix, 1f, 0f, 0f);
        mallet.bindData(colorShaderProgram);
        mallet.draw();

        Matrix.multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 0, puck.modelMatrix, 0);
        colorShaderProgram.userProgram();
        colorShaderProgram.setUniforms(modelViewProjectionMatrix, 0f, 1f, 0f);
        puck.bindData(colorShaderProgram);
        puck.draw();
    }

    public void handleTouchDown(float normalizedX, float normalizedY) {
//        Log.d(TAG, "handleTouchDown normalizedX*normalizedY == " + normalizedX + " " + normalizedY);
        // 我们为啥不直接拿malletPosition当Sphere的中心点？
        // 因为按照原计划木槌位置是跟着手指滑动而改变，所以单纯用初始化的malletPosition不够准确。
        Geometry.Sphere malletBoundingSphere = new Geometry.Sphere(
                new Geometry.Point(mallet.position.x, mallet.position.y, mallet.position.z),
                mallet.radius);

        Geometry.Ray ray = convertNormalized2DPointToRay(normalizedX, normalizedY);


        mallet.isPressed = Geometry.intersects(ray, malletBoundingSphere);
        if (mallet.isPressed) {
            Log.d(TAG, "handleTouchDown: " + mallet.isPressed);
        }
    }


    public void handleTouchMove(float normalizedX, float normalizedY) {
        Log.d(TAG, "handleTouchMove normalizedX*normalizedY == " + normalizedX + " " + normalizedY);
        if (mallet.isPressed) {
            mallet.previousPosition = mallet.position;
            // 根据屏幕触碰点 和 视图投影矩阵 产生三维射线
            Geometry.Ray ray = convertNormalized2DPointToRay(normalizedX, normalizedY);
            // 定义桌子的平面,观察平面的点为(0,0,0)
            Geometry.Plane tablePlane = new Geometry.Plane(new Geometry.Point(0, 0, 0), new Geometry.Vector(0, 0, 0));
            // 射线平米相交测试
            Geometry.Point touchPoint = Geometry.intersectionPoint(ray, tablePlane);
            // 根据相交点 更新木槌位置
            mallet.position = new Geometry.Point(touchPoint.x, touchPoint.y, touchPoint.z);
        }
    }


    private float clamp(float value, float min, float max) {
        return Math.min(max, Math.max(value, min));
    }

    private Geometry.Ray convertNormalized2DPointToRay(
            float normalizedX, float normalizedY) {

        final float[] nearPointNdc = {normalizedX, normalizedY, -1, 1};
        final float[] farPointNdc = {normalizedX, normalizedY, 1, 1};

        final float[] nearPointWorld = new float[4];
        final float[] farPointWorld = new float[4];

        Matrix.multiplyMV(nearPointWorld, 0, invertViewProjectionMatrix, 0, nearPointNdc, 0);
        Matrix.multiplyMV(farPointWorld, 0, invertViewProjectionMatrix, 0, farPointNdc, 0);

        divideByW(nearPointWorld);
        divideByW(farPointWorld);

        Geometry.Point nearPointRay = new Geometry.Point(nearPointWorld[0],
                nearPointWorld[1], nearPointWorld[2]);
        Geometry.Point farPointRay = new Geometry.Point(farPointWorld[0],
                farPointWorld[1], farPointWorld[2]);

        // 从nearPointRay出发，方向从nearPointRay指向farPointRay的射线
        return new Geometry.Ray(nearPointRay, Geometry.vectorBetween(nearPointRay, farPointRay));

    }

    private void divideByW(float[] vector) {
        vector[0] /= vector[3];
        vector[1] /= vector[3];
        vector[2] /= vector[3];
    }
}
