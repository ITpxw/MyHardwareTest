package com.strong.hardwaretest.view;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.strong.hardwaretest.utils.GpuUtil;

/**
 * Created by pxw on 2018/1/4.
 */

public class MyGLSurfaceView extends GLSurfaceView {

    GpuUtil mRenderer;
    public MyGLSurfaceView(Context context) {
        super(context);
        setEGLConfigChooser(8, 8, 8, 8, 0, 0);
        mRenderer = new GpuUtil();
        setRenderer(mRenderer);
    }

}
