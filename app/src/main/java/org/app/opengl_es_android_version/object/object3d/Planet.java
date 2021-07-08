package org.app.opengl_es_android_version.object.object3d;

import android.opengl.Matrix;

public class Planet extends Ball3D {

    float rotateCenterX, rotateCenterY, rotateCenterZ;

    public Planet() {
        super();
    }

    public Planet(float x, float y, float z, float radius) {
        super(x, y, z, radius);
    }

    public Planet(float x, float y, float z, float radius, int color) {
        super(x, y, z, radius, color);
    }

    public void startRotating(float centerX, float centerY, float centerZ) {
        rotateCenterX = centerX;
        rotateCenterY = centerY;
        rotateCenterZ = centerZ;
        Matrix.rotateM(modelMatrix, 0, 1, rotateCenterX, rotateCenterY, rotateCenterZ);
    }

}
