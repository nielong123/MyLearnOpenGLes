package org.app.opengl_es_android_version.object.object3d;

import android.content.Context;

public class Planet extends Ball3D{

    public Planet(Context context) {
        super(context);
    }

    public Planet(Context context, float x, float y, float z, float radius) {
        super(context, x, y, z, radius);
    }
}
