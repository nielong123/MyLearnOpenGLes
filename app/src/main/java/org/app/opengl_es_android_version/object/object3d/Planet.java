package org.app.opengl_es_android_version.object.object3d;

import android.opengl.Matrix;
import android.os.Handler;

import org.app.opengl_es_android_version.program.MyColorShaderProgram;

public class Planet extends Ball3D {

    float rotateCenterX, rotateCenterY, rotateCenterZ;

    long rotateSpeed;

    public Planet(MyColorShaderProgram colorShaderProgram) {
        super(colorShaderProgram);
    }

    public Planet(float x, float y, float z, float radius, MyColorShaderProgram colorShaderProgram) {
        super(x, y, z, radius, colorShaderProgram);
    }

    public Planet(float x, float y, float z, float radius, int color, MyColorShaderProgram colorShaderProgram) {
        super(x, y, z, radius, color, colorShaderProgram);
    }

    public void startRotating(float centerX, float centerY, float centerZ, long speed) {
        rotateCenterX = centerX;
        rotateCenterY = centerY;
        rotateCenterZ = centerZ;
        rotateSpeed = speed;
        handler.postDelayed(runnable, rotateSpeed);
    }

    public void stopRotating() {
        handler.removeCallbacks(runnable);
    }

    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Matrix.rotateM(modelMatrix, 0, 1, rotateCenterX, rotateCenterY, rotateCenterZ);
            handler.postDelayed(this, rotateSpeed);
        }
    };

}
