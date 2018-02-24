package com.strong.hardwaretest.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.strong.hardwaretest.R;
import com.strong.hardwaretest.application.SysApplication;

/**
 * Created by pxw on 2018/2/7.
 */

public class SplashActivity extends Activity{

    private Button btn_hardwareTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        btn_hardwareTest = (Button) findViewById(R.id.hardwareTest);

        btn_hardwareTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMachineCodeDialog();
            }
        });
    }

    //弹出输入机器编号的对话框
    private void createMachineCodeDialog() {
        //获取View对象
        View view = getLayoutInflater().inflate(R.layout.serialnum_input_dg, null);
        final Dialog mDialog = new Dialog(this, R.style.Dialog);
        //给对话框设置View和布局参数
        mDialog.setContentView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = mDialog.getWindow();                     //获取对话框的窗口
        WindowManager.LayoutParams wl = window.getAttributes();  //获得窗口的属性对象
        wl.x = 0;
        wl.y = 0;
        wl.width = 350;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        wl.gravity = Gravity.CENTER;
        mDialog.onWindowAttributesChanged(wl);     //设置对话框显示的窗口属性为wl
        mDialog.setCanceledOnTouchOutside(false);
        final EditText snEdt = (EditText) view.findViewById(R.id.machine_serialnum_edt);
        Button sure = (Button) view.findViewById(R.id.machine_code_sure_btn);
        Button cancel = (Button) view.findViewById(R.id.machine_code_cancel_btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.cancel();
                mDialog.dismiss();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serilNumber = snEdt.getText().toString().trim();
                if ((!"".equals(serilNumber))) {
                    SysApplication.getInstance().setReportName(serilNumber);
                } else {
                    Toast.makeText(SplashActivity.this , "请将信息填写完整" , Toast.LENGTH_LONG).show();
                }
                mDialog.cancel();
                mDialog.dismiss();
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                //finish();
            }
        });
        mDialog.show();
    }
}
