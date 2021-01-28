package org.app.opengl_es_android_version;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private GLSurfaceView glSurfaceView;
    private boolean rendererSet = false;

    HockeyRenderer3 hockeyRenderer = new HockeyRenderer3(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        glSurfaceView = new GLSurfaceView(this);
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
            glSurfaceView.setRenderer(hockeyRenderer);
            rendererSet = true;
        } else {
            Toast.makeText(this, "不支持openGL es 2.0", Toast.LENGTH_SHORT).show();
        }
        setContentView(glSurfaceView);
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


    /***
     * 我们在着色器中需要使用归一化设备坐标，因此我们需要把触控事件坐标转换回归一化设备坐标。
     * 因为安卓设备的屏幕竖直向下为y的正方向，这和OpenGL向上为正相反，这需要把y轴反转，并把每个坐标按比例映射到范围[-1，1]内。
     * （把实际触碰横坐标 event.getX 除以 视图的宽 得出 量化后的值 再乘以2 表示-1~0 和 0~1的两端范围，减1是把值控制在少于1的范围）
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event == null) {
            return false;
        }
        final float normalizedX = (event.getX() / v.getWidth()) * 2 - 1;
        final float normalizedY = (-(event.getY() / v.getHeight()) * 2 - 1);

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            glSurfaceView.queueEvent(new Runnable() {
                @Override
                public void run() {
                    hockeyRenderer.handleTouchDown(normalizedX, normalizedY);
                }
            });
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            glSurfaceView.queueEvent(new Runnable() {
                @Override
                public void run() {
                    hockeyRenderer.handleTouchMove(normalizedX, normalizedY);
                }
            });
        } else {
            return false;
        }
        return true;

    }
}
