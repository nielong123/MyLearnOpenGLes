package org.app.opengl_es_android_version.object.object3d;

import android.content.Context;
import android.opengl.Matrix;
import android.os.Handler;

public class Planet extends Ball3D {

    float rotateCenterX, rotateCenterY, rotateCenterZ;

    long rotateSpeed;

    public Planet(Context context) {
        super(context);
    }

    public Planet(Context context, float x, float y, float z, float radius) {
        super(context, x, y, z, radius);
    }

    public Planet(Context context, float x, float y, float z, float radius, int color) {
        super(context, x, y, z, radius, color);
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
