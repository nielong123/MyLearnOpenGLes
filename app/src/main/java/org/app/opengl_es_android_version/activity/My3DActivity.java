package org.app.opengl_es_android_version.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.config.MyConfigChooser;
import org.app.opengl_es_android_version.renderer.My3DRenderer_1;
import org.app.opengl_es_android_version.util.ScreenTools;

public class My3DActivity extends AppCompatActivity implements View.OnTouchListener {

    private final String TAG = My3DRenderer_1.class.getSimpleName();

    public GLSurfaceView glSurfaceView;
    private AppCompatSpinner functionListSp;


    private boolean rendererSet = false;

    private My3DRenderer_1 my3DRenderer1;

    float X, Y;
    boolean isZooming;
    double dis_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3d_demo);
        glSurfaceView = findViewById(R.id.glSurfaceView);
        functionListSp = findViewById(R.id.functionListSp);
        functionListSp.setOnItemSelectedListener(spTestListener);

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
            glSurfaceView.setEGLConfigChooser(new MyConfigChooser());
            glSurfaceView.setRenderer(my3DRenderer1);
            glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent != null) {
            final float normalizedX = ScreenTools.toOpenGLCoord(view, motionEvent.getX(), true);
            final float normalizedY = ScreenTools.toOpenGLCoord(view, motionEvent.getY(), false);
            switch (motionEvent.getActionMasked()) {
                //一个手指按下
                case MotionEvent.ACTION_DOWN:
                    X = normalizedX;
                    Y = normalizedY;
                    break;
                //另外一个副手指按下
                case MotionEvent.ACTION_POINTER_DOWN:
                    isZooming = true;
                    float x1 = ScreenTools.toOpenGLCoord(view, motionEvent.getX(1), true);
                    float y1 = ScreenTools.toOpenGLCoord(view, motionEvent.getY(1), false);
                    dis_start = ScreenTools.computeDis(normalizedX, x1, normalizedY, y1);
                    break;
                //移动
                case MotionEvent.ACTION_MOVE:
                    if (isZooming) {
                        float x2 = ScreenTools.toOpenGLCoord(view, motionEvent.getX(1), true);
                        float y2 = ScreenTools.toOpenGLCoord(view, motionEvent.getY(1), false);
                        double dis = ScreenTools.computeDis(normalizedX, x2, normalizedY, y2);
                        double scale = dis / dis_start;
                        my3DRenderer1.zoom((float) scale);
                        glSurfaceView.requestRender();
                        dis_start = dis;
                    } else {
                        my3DRenderer1.rotate(normalizedX - X, normalizedY - Y);
                        glSurfaceView.requestRender();
                        X = normalizedX;
                        Y = normalizedY;
                    }
                    break;
                //另外一个手指抬起
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

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            My3DActivity.this.glSurfaceView.requestRender();
        }
    };

    AdapterView.OnItemSelectedListener spTestListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    my3DRenderer1.resetViewProjection();
                    break;
                case 1:
                    my3DRenderer1.startMoonRotating();
                    break;
                case 2:
                    my3DRenderer1.stopMoonRotating();
                    break;
                case 3:
                    my3DRenderer1.getMoonCoordinate();
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Log.e(TAG, "onNothingSelected: ");
        }
    };

}
