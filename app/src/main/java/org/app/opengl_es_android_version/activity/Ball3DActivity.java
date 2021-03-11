package org.app.opengl_es_android_version.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.renderer.BallRenderer;

public class Ball3DActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;

    private boolean rendererSet = false;

    private BallRenderer ballRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cube);
        glSurfaceView = findViewById(R.id.glSurfaceView);
        ActivityManager activityManager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo deviceConfigurationInfo =
                activityManager.getDeviceConfigurationInfo();
        final boolean supportEs2 = deviceConfigurationInfo.reqGlEsVersion >= 0x20000 || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")));
        if (supportEs2) {
            glSurfaceView.setEGLContextClientVersion(2);
            ballRenderer = new BallRenderer(this);
            glSurfaceView.setRenderer(ballRenderer);
            glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
            rendererSet = true;
        } else {
            Toast.makeText(this, "不支持openGL es 2.0", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (rendererSet) {
            glSurfaceView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (rendererSet) {
            glSurfaceView.onPause();
        }
    }
}
