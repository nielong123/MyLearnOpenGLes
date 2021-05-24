package org.app.opengl_es_android_version.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.renderer.My3DRenderer_1;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class My3DActivity extends AppCompatActivity implements View.OnTouchListener {

    private GLSurfaceView glSurfaceView;
    private Button button1;

    private boolean rendererSet = false;

    private My3DRenderer_1 my3DRenderer1;

    float X, Y;
    boolean isZooming;
    double dis_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cube);
        glSurfaceView = findViewById(R.id.glSurfaceView);
        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(testListener);
        glSurfaceView.setOnTouchListener(this);

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
            my3DRenderer1 = new My3DRenderer_1(this);
            glSurfaceView.setRenderer(my3DRenderer1);
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

    public View.OnClickListener testListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            my3DRenderer1.resetViewProjection();
        }
    };

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent != null) {
            final float normalizedX = toOpenGLCoord(view, motionEvent.getX(), true);
            final float normalizedY = toOpenGLCoord(view, motionEvent.getY(), false);
            switch (motionEvent.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    X = normalizedX;
                    Y = normalizedY;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    isZooming = true;
                    float x1 = toOpenGLCoord(view, motionEvent.getX(1), true);
                    float y1 = toOpenGLCoord(view, motionEvent.getY(1), false);
                    dis_start = computeDis(normalizedX, x1, normalizedY, y1);

                    break;
                case MotionEvent.ACTION_MOVE:
                    if (isZooming) {
                        float x2 = toOpenGLCoord(view, motionEvent.getX(1), true);
                        float y2 = toOpenGLCoord(view, motionEvent.getY(1), false);
                        double dis = computeDis(normalizedX, x2, normalizedY, y2);
                        double scale = dis / dis_start;
                        my3DRenderer1.zoom((float) scale);
                        dis_start = dis;
                    } else {
                        my3DRenderer1.rotate(normalizedX - X, normalizedY - Y);
                        X = normalizedX;
                        Y = normalizedY;
                    }
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    isZooming = false;
                    X = normalizedX;
                    Y = normalizedY;
                    break;
                case MotionEvent.ACTION_UP:
                default:
                    break;
            }
            return true;
        }
        return false;
    }

    /**
     * 屏幕坐标系点转OpenGL坐标系
     *
     * @return
     */
    private static float toOpenGLCoord(View view, float value, boolean isWidth) {
        if (isWidth) {
            return (value / (float) view.getWidth()) * 2 - 1;
        } else {
            return -((value / (float) view.getHeight()) * 2 - 1);
        }
    }

    /**
     * 计算两个点之间的距离
     *
     * @param x1
     * @param x2
     * @param y1
     * @param y2
     * @return
     */
    private static double computeDis(float x1, float x2, float y1, float y2) {
        return sqrt(pow((x2 - x1), 2) + pow((y2 - y1), 2));
    }
}
