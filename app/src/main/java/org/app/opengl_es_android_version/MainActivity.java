package org.app.opengl_es_android_version;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.app.opengl_es_android_version.activity.FBOActivity;
import org.app.opengl_es_android_version.activity.My3DActivity;
import org.app.opengl_es_android_version.activity.CubeActivity;
import org.app.opengl_es_android_version.activity.HockeyActivity;
import org.app.opengl_es_android_version.activity.My2DActivity;
import org.app.opengl_es_android_version.activity.PanoramaActivity;
import org.app.opengl_es_android_version.activity.PictureTextureActivity;
import org.app.opengl_es_android_version.object.fbo.FboTestActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnTest1).setOnClickListener(this);
        findViewById(R.id.btnTest2).setOnClickListener(this);
        findViewById(R.id.btnTest3).setOnClickListener(this);
        findViewById(R.id.btnTest4).setOnClickListener(this);
        findViewById(R.id.btnTest5).setOnClickListener(this);
        findViewById(R.id.btnTest6).setOnClickListener(this);
        findViewById(R.id.btnTest7).setOnClickListener(this);
    }

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
            case R.id.btnTest3:
                intent.setClass(this, PanoramaActivity.class);
                startActivity(intent);
                return;
            case R.id.btnTest4:
                intent.setClass(this, My2DActivity.class);
                startActivity(intent);
                return;
            case R.id.btnTest5:
                intent.setClass(this, My3DActivity.class);
                startActivity(intent);
                return;
            case R.id.btnTest6:
                intent.setClass(this, PictureTextureActivity.class);
                startActivity(intent);
                return;
            case R.id.btnTest7:
//                intent.setClass(this, FboTestActivity.class);
                intent.setClass(this, FBOActivity.class);
                startActivity(intent);
                return;
        }
    }
}
