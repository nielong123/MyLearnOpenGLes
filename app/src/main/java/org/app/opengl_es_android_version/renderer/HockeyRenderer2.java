package org.app.opengl_es_android_version.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.object.Mallet;
import org.app.opengl_es_android_version.object.Puck;
import org.app.opengl_es_android_version.object.Table;
import org.app.opengl_es_android_version.program.ColorShaderProgram;
import org.app.opengl_es_android_version.program.TextureShaderProgram;
import org.app.opengl_es_android_version.util.MatrixHelper;
import org.app.opengl_es_android_version.util.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class HockeyRenderer2 implements GLSurfaceView.Renderer {

    String TAG = this.getClass().getSimpleName();

    Context context;

    Table table;
    Mallet mallet;
    Puck puck;
    TextureShaderProgram textureShaderProgram;
    ColorShaderProgram colorShaderProgram;

    int textureId;


    private final float[] modelViewProjectionMatrix = new float[16];

    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    private final float[] viewProjectionMatrix = new float[16];


    public HockeyRenderer2(Context context) {
        this.context = context;
        Matrix.setIdentityM(projectionMatrix, 0);
        Matrix.setIdentityM(viewMatrix, 0);
        Matrix.setIdentityM(viewProjectionMatrix, 0);
        Matrix.setIdentityM(modelViewProjectionMatrix, 0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        table = new Table();
        mallet = new Mallet(0.08f, 0.15f, 32);
        puck = new Puck(0.06f, 0.02f, 32);

        textureShaderProgram = new TextureShaderProgram(context);
        colorShaderProgram = new ColorShaderProgram(context);

        textureId = TextureHelper.loadTexture(context, R.drawable.ic_launcher_background);
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

        Matrix.rotateM(table.modelMatrix, 0, -90f, 1f, 0f, 0f);
        Matrix.translateM(mallet.modelMatrix, 0, 0f, mallet.height, 0.5f);
        Matrix.translateM(puck.modelMatrix, 0, 0f, puck.height, 0f);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        Matrix.multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 0, table.modelMatrix, 0);
        textureShaderProgram.userProgram();
        textureShaderProgram.setUniforms(modelViewProjectionMatrix, textureId);
        table.bindData(textureShaderProgram);
        table.draw();

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
        Log.d(TAG, "handleTouchDown normalizedX*normalizedY == " + normalizedX + " " + normalizedY);
    }


    public void handleTouchMove(float normalizedX, float normalizedY) {
        Log.d(TAG, "handleTouchMove normalizedX*normalizedY == " + normalizedX + " " + normalizedY);
    }
}
