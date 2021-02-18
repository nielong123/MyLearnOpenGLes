package org.app.opengl_es_android_version.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import org.app.opengl_es_android_version.object.Cube;
import org.app.opengl_es_android_version.program.CubeShaderProgram;
import org.app.opengl_es_android_version.util.MatrixHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CubeRenderer implements GLSurfaceView.Renderer {

    private final Context context;
    private final float[] modelViewProjectMatrix = new float[16];
    private final float[] viewProjectMatrix = new float[16];
    private final float[] projectMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    CubeShaderProgram cubeShaderProgram;

    Cube cube;

    public CubeRenderer(Context context) {
        this.context = context;

        Matrix.setIdentityM(projectMatrix, 0);
        Matrix.setIdentityM(viewMatrix, 0);
        Matrix.setIdentityM(viewProjectMatrix, 0);
        Matrix.setIdentityM(modelViewProjectMatrix, 0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0, 0, 0, 0);

        cube = new Cube();
        cubeShaderProgram = new CubeShaderProgram(context);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        MatrixHelper.perspectiveM(projectMatrix, 45, (float) width / (float) height, 1f, 100f);
        Matrix.setLookAtM(viewMatrix, 0,
                4f, 4f, 4f,
                0f, 0f, 0f,
                0f, 1f, 0f);
        Matrix.multiplyMM(viewProjectMatrix, 0, projectMatrix, 0, viewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        Matrix.multiplyMM(modelViewProjectMatrix, 0, viewProjectMatrix, 0, cube.modelMatrix, 0);
        cubeShaderProgram.userProgram();
        cubeShaderProgram.setUniforms(modelViewProjectMatrix);
        cube.bindData(cubeShaderProgram);
        cube.draw();
    }
}
