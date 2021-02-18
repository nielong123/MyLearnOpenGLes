package org.app.opengl_es_android_version;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.app.opengl_es_android_version.activity.CubeActivity;
import org.app.opengl_es_android_version.activity.HockeyActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnTest1).setOnClickListener(this);
        findViewById(R.id.btnTest2).setOnClickListener(this);
    }



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
                intent.setClass(this, HockeyActivity.class);
                startActivity(intent);
                return;
        }
    }
}
