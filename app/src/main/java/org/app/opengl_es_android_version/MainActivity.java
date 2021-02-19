package org.app.opengl_es_android_version;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import org.app.opengl_es_android_version.activity.CubeActivity;
import org.app.opengl_es_android_version.activity.HockeyActivity;
import org.app.opengl_es_android_version.activity.My2DActivity;
import org.app.opengl_es_android_version.activity.PanoramaActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnTest1).setOnClickListener(this);
        findViewById(R.id.btnTest2).setOnClickListener(this);
        findViewById(R.id.btnTest3).setOnClickListener(this);
        findViewById(R.id.btnTest4).setOnClickListener(this);
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
        }
    }
}
