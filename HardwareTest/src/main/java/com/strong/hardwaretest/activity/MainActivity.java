package com.strong.hardwaretest.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.strong.hardwaretest.R;
import com.strong.hardwaretest.application.SysApplication;
import com.strong.hardwaretest.fragment.BaseInfoFragment;
import com.strong.hardwaretest.fragment.InterfaceTestFragment;
import com.strong.hardwaretest.fragment.LedSpkTestFragment;
import com.strong.hardwaretest.fragment.NetTestFragment;
import com.strong.hardwaretest.utils.FileUtil;
import com.strong.hardwaretest.view.TitleBarView;


public class MainActivity extends FragmentActivity {

    protected static final String TAG = "MainActivity";
    private String rootPath = "/mnt/media_rw/udisk/";
    private Context mContext;
    private ImageButton mBaseInfo, mNetTest, mInterfaceTest, mLedSpkTest;
    private View currentButton;

    private LinearLayout buttomBarGroup;    //底部栏
    private TitleBarView mTitleBarView;

    private View mPopView;
    private PopupWindow mPopupWindow;
    private ImageView mGenerateReport, mViewReport;
    private FileUtil fileUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        Log.i(TAG, "rootpath:" + Environment.getExternalStorageDirectory().getAbsolutePath());

        findView();
        initTitleView();

        init();
    }

    private void findView() {
        buttomBarGroup = (LinearLayout) findViewById(R.id.buttom_bar_group);

        mTitleBarView = (TitleBarView) findViewById(R.id.title_bar);

        mPopView = LayoutInflater.from(mContext).inflate(R.layout.fragment_report_pop, null);

        mBaseInfo = (ImageButton) findViewById(R.id.buttom_baseinfo);        //主页ImageButton
        mNetTest = (ImageButton) findViewById(R.id.buttom_nettest);
        mInterfaceTest = (ImageButton) findViewById(R.id.buttom_interfacetest);
        mLedSpkTest = (ImageButton) findViewById(R.id.buttom_ledspk);

        mGenerateReport = (ImageView) mPopView.findViewById(R.id.pop_generate_report);
        mViewReport = (ImageView) mPopView.findViewById(R.id.pop_view_report);

    }

    private void initTitleView() {
        mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.VISIBLE);

        mTitleBarView.setTitleText(R.string.hardwaretest);

        mTitleBarView.setBtnRight(R.mipmap.down);
        //点击右侧按钮 显示弹出生成报告和查看报告选项
        mTitleBarView.setBtnRightOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitleBarView.setPopWindow(mPopupWindow, mTitleBarView);

            }
        });

        mPopupWindow = new PopupWindow(mPopView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mTitleBarView.setBtnRight(R.mipmap.down);
            }
        });
    }

    private void init() {

        final String reportName = SysApplication.getInstance().getReportName() + ".txt";

        fileUtil = new FileUtil(mContext);
        //设置底部栏的点击事件
        mBaseInfo.setOnClickListener(homeOnClickListener);
        mNetTest.setOnClickListener(testOnClickListener);
        mInterfaceTest.setOnClickListener(dataOnClickListener);
        mLedSpkTest.setOnClickListener(mysettingOnClickListener);

        mBaseInfo.performClick();

        mGenerateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SysApplication.getInstance().setReportContent();
                String reportContent = SysApplication.getInstance().getReportContent();
                Log.i(TAG, "reportContent:" + reportContent);
                boolean writeFlag = fileUtil.writeTxtToFile(reportContent, rootPath, reportName);
                if(writeFlag){
                    Toast.makeText(mContext,"测试报告生成成功！",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mContext,"测试报告生成失败！",Toast.LENGTH_SHORT).show();
                }
                SysApplication.getInstance().cleanReportContent();
                mPopupWindow.dismiss();
            }
        });

        mViewReport.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fileUtil.openFiles(rootPath + reportName);
                mPopupWindow.dismiss();

            }
        });
    }

    /**
     * 主页的点击事件
     */
    private View.OnClickListener homeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            BaseInfoFragment homeFatherFragment = new BaseInfoFragment();
            ft.replace(R.id.fl_content, homeFatherFragment, MainActivity.TAG);
            ft.commit();
            setButton(v);
        }
    };

    private View.OnClickListener testOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            NetTestFragment testFragment = new NetTestFragment();
            ft.replace(R.id.fl_content, testFragment, MainActivity.TAG);
            ft.commit();
            setButton(v);

        }
    };

    private View.OnClickListener dataOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            InterfaceTestFragment dataFatherFragment = new InterfaceTestFragment();
            ft.replace(R.id.fl_content, dataFatherFragment, MainActivity.TAG);
            ft.commit();
            setButton(v);

        }
    };

    private View.OnClickListener mysettingOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            LedSpkTestFragment mySettingFragment = new LedSpkTestFragment();
            ft.replace(R.id.fl_content, mySettingFragment, MainActivity.TAG);
            ft.commit();
            setButton(v);

        }
    };

    private void setButton(View v) {
        if (currentButton != null && currentButton.getId() != v.getId()) {
            currentButton.setEnabled(true);
        }
        v.setEnabled(false);
        currentButton = v;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /*public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("确定退出吗？")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create()
                .show();
    }*/


}
