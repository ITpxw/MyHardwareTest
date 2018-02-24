package com.strong.hardwaretest.application;

import android.app.Application;

/**
 * 自定义application
 */

public class SysApplication extends Application {

    private static SysApplication instance;

    private String reportName = "";
    private String baseInfo = "";
    private String netTestInfo = "";
    private String interfaceInfo = "";
    private String ledspkInfo = "";

    private StringBuilder sb = new StringBuilder();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    //获得单例对象
    public synchronized static SysApplication getInstance() {
        if (null == instance) {
            instance = new SysApplication();
        }
        return instance;
    }


    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public String getReportContent() {
        return sb.toString();
    }

    public void setReportContent() {
        sb.append(baseInfo + netTestInfo + interfaceInfo + ledspkInfo);
    }

    public void cleanReportContent() {
        sb.delete(0, sb.length());
    }

    public void setBaseInfo(String baseInfo) {
        this.baseInfo = baseInfo;
    }

    public void setNetTestInfo(String netTestInfo) {
        this.netTestInfo = netTestInfo;
    }

    public void setInterfaceInfo(String interfaceInfo) {
        this.interfaceInfo = interfaceInfo;
    }

    public void setLedspkInfo(String ledspkInfo) {
        this.ledspkInfo = ledspkInfo;
    }

    public String getBaseInfo() {
        return baseInfo;
    }

    public String getNetTestInfo() {
        return netTestInfo;
    }

    public String getInterfaceInfo() {
        return interfaceInfo;
    }

    public String getLedspkInfo() {
        return ledspkInfo;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
}