package com.strong.hardwaretest.activity;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.strong.hardwaretest.R;
import com.strong.hardwaretest.view.MyGLSurfaceView;

public class GpuInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView glView = new MyGLSurfaceView(this);
        setContentView(glView);
    }
}
