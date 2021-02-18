package org.app.opengl_es_android_version;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.app.opengl_es_android_version.activity.CubeActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnTest1).setOnClickListener(this);
        findViewById(R.id.btnTest2).setOnClickListener(this);
    }


    /***
     * 我们在着色器中需要使用归一化设备坐标，因此我们需要把触控事件坐标转换回归一化设备坐标。
     * 因为安卓设备的屏幕竖直向下为y的正方向，这和OpenGL向上为正相反，这需要把y轴反转，并把每个坐标按比例映射到范围[-1，1]内。
     * （把实际触碰横坐标 event.getX 除以 视图的宽 得出 量化后的值 再乘以2 表示-1~0 和 0~1的两端范围，减1是把值控制在少于1的范围）
     * @param v
     * @param event
     * @return
     */
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        if (event == null) {
//            return false;
//        }
//        final float normalizedX = (event.getX() / v.getWidth()) * 2 - 1;
//        final float normalizedY = -((event.getY() / v.getHeight()) * 2 - 1);
//
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            glSurfaceView.queueEvent(new Runnable() {
//                @Override
//                public void run() {
//                    hockeyRenderer.handleTouchDown(normalizedX, normalizedY);
//                }
//            });
//        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
//            glSurfaceView.queueEvent(new Runnable() {
//                @Override
//                public void run() {
//                    hockeyRenderer.handleTouchMove(normalizedX, normalizedY);
//                }
//            });
//        } else {
//            return false;
//        }
//        return true;
//    }
    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btnTest1:
                intent.setClass(this, CubeActivity.class);
                startActivity(intent);
                return;
            case R.id.btnTest2:
                intent.setClass(this, CubeActivity.class);
                startActivity(intent);
                return;
        }
    }
}
