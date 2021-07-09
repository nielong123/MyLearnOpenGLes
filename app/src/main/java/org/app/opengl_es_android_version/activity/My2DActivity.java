package org.app.opengl_es_android_version.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.app.opengl_es_android_version.R;
import org.app.opengl_es_android_version.renderer.My2DRenderer_1;

import java.nio.ByteBuffer;

import static android.opengl.GLSurfaceView.RENDERMODE_WHEN_DIRTY;

public class My2DActivity extends AppCompatActivity implements View.OnTouchListener {

    boolean rendererSet;

    ImageView mirrorIv;
    GLSurfaceView glSurfaceView;

    My2DRenderer_1 my2DRenderer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cube);

        mirrorIv = findViewById(R.id.mirrorIv);
        glSurfaceView = findViewById(R.id.glSurfaceView);
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
            my2DRenderer = new My2DRenderer_1(this);
            my2DRenderer.setCallback(callback);
            glSurfaceView.setRenderer(my2DRenderer);
            glSurfaceView.setRenderMode(RENDERMODE_WHEN_DIRTY);
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

    My2DRenderer_1.Callback callback = new My2DRenderer_1.Callback() {
        @Override
        public void onReadPixData(final ByteBuffer data, final int width, final int height) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.e("wuwang", "callback success");
                    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    bitmap.copyPixelsFromBuffer(data);
                    saveBitmap(bitmap);
                    data.clear();
                }
            }).start();
        }

    };

    /***
     * 我们在着色器中需要使用归一化设备坐标，因此我们需要把触控事件坐标转换回归一化设备坐标。
     * 因为安卓设备的屏幕竖直向下为y的正方向，这和OpenGL向上为正相反，这需要把y轴反转，并把每个坐标按比例映射到范围[-1，1]内。
     * （把实际触碰横坐标 event.getX 除以 视图的宽 得出 量化后的值 再乘以2 表示-1~0 和 0~1的两端范围，减1是把值控制在少于1的范围）
     * @param v
     * @param event
     * @return
     */
    float oldX, oldY;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event == null) {
            return false;
        }
        final float normalizedX = (event.getX() / v.getWidth()) * 2 - 1;
        final float normalizedY = -((event.getY() / v.getHeight()) * 2 - 1);

        switch (event.getAction()) {
            //单点下落
            case MotionEvent.ACTION_DOWN:
                glSurfaceView.queueEvent(new Runnable() {
                    @Override
                    public void run() {
                        my2DRenderer.handleTouchDown(normalizedX, normalizedY);
                        my2DRenderer.handleTouchMove(-1,
                                -1,
                                MotionEvent.ACTION_DOWN);
                    }
                });
                break;
            //2～n个点下落
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            //第2~n个触点离开屏幕
            case MotionEvent.ACTION_POINTER_UP:
                break;
            //所有触点(最后一个触点)离开屏幕
            case MotionEvent.ACTION_UP:
                oldY = 0;
                oldX = 0;
                my2DRenderer.handleTouchMove(-1,
                        -1,
                        MotionEvent.ACTION_UP);
                break;
            case MotionEvent.ACTION_MOVE:
                if (oldY == 0 && oldX == 0) {
                    oldY = event.getY();
                    oldX = event.getX();
                    return false;
                }
                final float transX = event.getX() - oldX;
                final float transY = event.getY() - oldY;
                glSurfaceView.queueEvent(new Runnable() {
                    @Override
                    public void run() {
                        my2DRenderer.handleTouchMove(transX / 100,
                                -transY / 100,
                                MotionEvent.ACTION_MOVE);
                        glSurfaceView.requestRender();
                    }
                });
                oldX = event.getX();
                oldY = event.getY();
                break;
        }
        return true;
    }

    public void onClick(View v) {
        my2DRenderer.resetMatrix();
        glSurfaceView.requestRender();
    }

    public void onMakeMirror(View view) {
        my2DRenderer.setMirror(true);
        glSurfaceView.requestRender();
    }

    //图片保存
    public void saveBitmap(final Bitmap b) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(FBOActivity.this, "保存成功->"+jpegName, Toast.LENGTH_SHORT).show();
                mirrorIv.setImageBitmap(b);
            }
        });
    }
}
