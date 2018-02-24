package com.strong.hardwaretest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.strong.hardwaretest.R;
import com.strong.hardwaretest.application.SysApplication;
import com.strong.hardwaretest.utils.AndroidUtil;
import com.strong.hardwaretest.utils.CpuUtil;
import com.strong.hardwaretest.utils.NetTestUtil;
import com.strong.hardwaretest.utils.SystemUtil;

/**
 * 基本信息
 */
public class BaseInfoFragment extends Fragment {

    private Context mContext;
    private View mBaseView;
    private TextView mTvBaseInfo;

    private View mPopView;
    private PopupWindow mPopupWindow;
    private ImageView mGenerateReport, mViewReport;
    private RelativeLayout mCanversLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        mBaseView = inflater.inflate(R.layout.fragment_baseinfo, null);

        mPopView = inflater.inflate(R.layout.fragment_report_pop, null);
        findView();
        init();

        return mBaseView;
    }

    private void findView() {
        mTvBaseInfo = (TextView) mBaseView.findViewById(R.id.tv_baseinfo);
    }


    private void init() {

        showBaseParameter();
    }

    private void showBaseParameter() {

        String baseInfo = "";
        baseInfo += "-------------------------------------------硬件测试报告----------------------------------------------------------------------" + "\n";
        baseInfo += "系统信息：" + "\n";
        baseInfo += "\t" + "制造商: \t" + SystemUtil.getManufacturer() + "\n";
        baseInfo += "\t" + "机型: \t" + SystemUtil.getModel() + "\n";
        baseInfo += "\t" + "品牌: \t" + SystemUtil.getBrand() + "\n";
        baseInfo += "\t" + "主板: \t" + SystemUtil.getBoard() + "\n";
        baseInfo += "\t" + "设备: \t" + SystemUtil.getDevice() + "\n";
        baseInfo += "\t" + "硬件: \t" + SystemUtil.getHardware() + "\n";
        baseInfo += "\t" + "产品: \t" + SystemUtil.getProduct() + "\n";
        baseInfo += "\t" + "序列号: \t" + SystemUtil.getSerialNumber() + "\n";
        baseInfo += "\t" + "已安装内存: \t" + SystemUtil.getTotalRam(mContext) + "\n";
        baseInfo += "\t" + "总内存: \t\t" + SystemUtil.getTotalMemory(mContext) + "\n";
        baseInfo += "\t" + "可用内存: \t" + SystemUtil.getAvailableMemory(mContext) + "\n";
        baseInfo += "\t" + "内部存储总空间: \t" + SystemUtil.getStorageToatalSpace(mContext) + "\n";
        baseInfo += "\t" + "内部存储可用空间: \t" + SystemUtil.getStorageAvailableSpace(mContext) + "\n";

        baseInfo += "--------------------------------------------------------------------------------------------------------------------------" + "\n";
        baseInfo += "CPU信息：" + "\n";
        baseInfo += "\t" + "CPU核心数: \t" + CpuUtil.getCpuCoreNum() + "\n";
        baseInfo += "\t" + "CPU架构: \t" + CpuUtil.getCpuABI() + "\n";
        baseInfo += "\t" + "CPU名称: \t" + CpuUtil.getCpuName() + "\n";
        baseInfo += "\t" + "CPU最大工作频率: \t" + CpuUtil.getMaxCpuFreq() + "\n";
        baseInfo += "\t" + "CPU最小工作频率: \t" + CpuUtil.getMinCpuFreq() + "\n";


        baseInfo += "-------------------------------------------------------------------------------------------------------------------------" + "\n";
        baseInfo += "Android系统信息：" + "\n";
        baseInfo += "\t" + "Android版本：\t" + AndroidUtil.getSystemVersion() + "\n";
        baseInfo += "\t" + "API级别：\t\t" + AndroidUtil.getSystemApi() + "\n";
        baseInfo += "\t" + "已ROOT设备：\t" + AndroidUtil.isRoot() + "\n";
        baseInfo += "\t" + "Android ID：\t" + AndroidUtil.getAndroidId(mContext) + "\n";
        baseInfo += "\t" + "构建号：\t\t" + AndroidUtil.getDisplay() + "\n";
        baseInfo += "\t" + "代号：\t\t" + AndroidUtil.getCodeName() + "\n";
        baseInfo += "\t" + "指纹：\t\t" + AndroidUtil.getFingerprint() + "\n";
        baseInfo += "\t" + "增量：\t\t" + AndroidUtil.getIncremental() + "\n";
        baseInfo += "\t" + "内核版本：\t" + AndroidUtil.getLinuxCoreVerison() + "\n";
        baseInfo += "\t" + "标签：\t\t" + AndroidUtil.getTags() + "\n";
        baseInfo += "\t" + "类型：\t\t" + AndroidUtil.getType() + "\n";
        baseInfo += "\t" + "Android 语言：\t" + AndroidUtil.getSystemLanguage() + "\n";
        baseInfo += "\t" + "时区：\t\t" + AndroidUtil.getCurrentTimeZone() + "\n";
        baseInfo += "\t" + "检测扬声器：\t" + NetTestUtil.isSpeakerGood(mContext) + "\n";
        baseInfo += "\t" + "检测蓝牙：\t" + NetTestUtil.bluetoothIsGood() + "\n";
        baseInfo += "\t" + "检测WIFI：\t" + NetTestUtil.isWifiIsGood(mContext) + "\n";
        baseInfo += "\t" + "网络连接类型：\t" + NetTestUtil.getNetWorkState(mContext) + "\n";

        mTvBaseInfo.setText(baseInfo.substring(120, baseInfo.length()));
        SysApplication.getInstance().setBaseInfo(baseInfo);
    }

    /*private void showBaseParameter() {

        String phoneInfo = "";
        phoneInfo += "-------------------------------------------硬件测试报告----------------------------------------------------------------------" + "\n";
        phoneInfo += "系统信息：" + "\n";
        phoneInfo += "\t" + "制造商(MANUFACTURER): " + "          " + SystemUtil.getManufacturer() + "\n";
        phoneInfo += "\t" + "机型(MODEL): " + "                   " + SystemUtil.getModel() + "\n";
        phoneInfo += "\t" + "品牌(BRAND): " + "                   " + SystemUtil.getBrand() + "\n";
        phoneInfo += "\t" + "主板(BOARD): " + "                   " + SystemUtil.getBoard() + "\n";
        phoneInfo += "\t" + "设备(DEVICE): " + "                  " + SystemUtil.getDevice() + "\n";
        phoneInfo += "\t" + "硬件(HARDWARE): " + "                " + SystemUtil.getHardware() + "\n";
        phoneInfo += "\t" + "产品(PRODUCT): " + "                         \t" + SystemUtil.getProduct() + "\n";
        phoneInfo += "\t" + "序列号(SerialNumber): " + "                  \t" + SystemUtil.getSerialNumber() + "\n";
        phoneInfo += "\t" + "已安装内存(TotalMemory): " + "                \t" + SystemUtil.getTotalRam(mContext) + "\n";
        phoneInfo += "\t" + "总内存(TotalMemory): " + "                   \t" + SystemUtil.getTotalMemory(mContext) + "\n";
        phoneInfo += "\t" + "可用内存(AvailMemory): " + "                 \t" + SystemUtil.getAvailableMemory(mContext) + "\n";
        phoneInfo += "\t" + "内部存储总空间(InternalToatalSpace): " + "    \t" + SystemUtil.getStorageToatalSpace(mContext) + "\n";
        phoneInfo += "\t" + "内部存储可用空间(InternalAvailableSpace): " + "\t" + SystemUtil.getStorageAvailableSpace(mContext) + "\n";

        phoneInfo += "--------------------------------------------------------------------------------------------------------------------------" + "\n";
        phoneInfo += "CPU信息：" + "\n";
        phoneInfo += "\t" + "CPU核心数: " + "\t" + CpuUtil.getCpuCoreNum() + "\n";
        phoneInfo += "\t" + "CPU架构: " + "\t" + CpuUtil.getCpuABI() + "\n";
        phoneInfo += "\t" + "CPU名称: " + "\t" + CpuUtil.getCpuName() + "\n";
        phoneInfo += "\t" + "CPU最大工作频率: " + "\t" + CpuUtil.getMaxCpuFreq() + "\n";
        phoneInfo += "\t" + "CPU最小工作频率: " + "\t" + CpuUtil.getMinCpuFreq() + "\n";


        phoneInfo += "-------------------------------------------------------------------------------------------------------------------------" + "\n";
        phoneInfo += "Android系统信息：" + "\n";
        phoneInfo += "\t" + "Android版本：" + "\t" + AndroidUtil.getSystemVersion() + "\n";
        phoneInfo += "\t" + "API级别：" + "\t" + "\t" + AndroidUtil.getSystemApi() + "\n";
        phoneInfo += "\t" + "已ROOT设备：" + "\t" + AndroidUtil.isRoot() + "\n";
        phoneInfo += "\t" + "Android ID：" + "\t" + AndroidUtil.getAndroidId(mContext) + "\n";
        phoneInfo += "\t" + "构建号：" + "\t" + "\t" + AndroidUtil.getDisplay() + "\n";
        phoneInfo += "\t" + "代号：" + "\t" + "\t" + AndroidUtil.getCodeName() + "\n";
        phoneInfo += "\t" + "指纹：" + "\t" + "\t" + AndroidUtil.getFingerprint() + "\n";
        phoneInfo += "\t" + "增量：" + "\t" + "\t" + AndroidUtil.getIncremental() + "\n";
        phoneInfo += "\t" + "内核版本：" + "\t" + AndroidUtil.getLinuxCoreVerison() + "\n";
        phoneInfo += "\t" + "标签：" + "\t" + "\t" + AndroidUtil.getTags() + "\n";
        phoneInfo += "\t" + "类型：" + "\t" + "\t" + AndroidUtil.getType() + "\n";
        phoneInfo += "\t" + "Android 语言：" + "\t" + AndroidUtil.getSystemLanguage() + "\n";
        phoneInfo += "\t" + "时区：" + "\t" + "\t" + AndroidUtil.getCurrentTimeZone() + "\n";
        phoneInfo += "\t" + "正常运行时间：" + "\t" + AndroidUtil.getSystemRunTime() + "\n";
        phoneInfo += "\t" + "检测扬声器：" + "\t" + NetTestUtil.isSpeakerGood(mContext) + "\n";
        phoneInfo += "\t" + "检测蓝牙：" + "\t" + NetTestUtil.bluetoothIsGood() + "\n";
        phoneInfo += "\t" + "检测WIFI：" + "\t" + NetTestUtil.isWifiIsGood(mContext) + "\n";
        phoneInfo += "\t" + "网络连接类型：" + "\t" + NetTestUtil.getNetWorkState(mContext) + "\n";

        mTvBaseInfo.setText(phoneInfo.substring(120,phoneInfo.length()));
        //SysApplication.getInstance().setReportContent(mTvBaseInfo.getText().toString());
        SysApplication.getInstance().setBaseInfo(phoneInfo);
    }*/


}
