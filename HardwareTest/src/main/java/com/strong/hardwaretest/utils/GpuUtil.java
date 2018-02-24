package com.strong.hardwaretest.utils;

import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by pxw on 2018/1/4.
 */

public class GpuUtil implements GLSurfaceView.Renderer {
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        //供应商
        Log.i("GPUInfo", "GL_VENDOR::::: " + gl.glGetString(GL10.GL_VENDOR));
        //渲染器
        Log.i("GPUInfo", "GL_RENDERER:::::" +gl.glGetString(GL10.GL_RENDERER));
        //OpenGL ES版本
        Log.i("GPUInfo", "GL_VERSION::::: " + gl.glGetString(GL10.GL_VERSION));
        //OpenGL ES扩展
        Log.i("GPUInfo", "GL_EXTENSIONS::::: " + gl.glGetString(GL10.GL_EXTENSIONS));
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {

    }

    @Override
    public void onDrawFrame(GL10 gl10) {

    }
}
