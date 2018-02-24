package com.strong.hardwaretest.utils;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by pxw on 2018/1/4.
 */

public class AndroidUtil {
    /**
     * 获取系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取系统API级别
     * @return 级别
     */
    public static int getSystemApi() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 判断是否ROOT
     */
    public static String isRoot() {

        boolean root = false;

        try {
            if ((!new File("/system/bin/su").exists())
                    && (!new File("/system/xbin/su").exists())) {
                root = false;
            } else {
                root = true;
            }

        } catch (Exception e) {
        }

        if(root){
            return "是";
        }else {
            return "否";
        }
    }

    /**
     * 获取Android ID
     * @param context
     * @return id
     */
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取构建号
     * @return 构建号
     */
    public static String getDisplay() {
        return Build.DISPLAY;
    }

    /**
     * 获取代号
     * @return 代号
     */
    public static String getCodeName() {
        return Build.VERSION.CODENAME;
    }

    /**
     * 获取指纹
     * @return 指纹
     */
    public static String getFingerprint() {
        return Build.FINGERPRINT;
    }

    /**
     * 获取ID
     * @return ID
     */
    public static String getId() {
        return Build.ID;
    }

    /**
     * 获取增量
     * @return
     */
    public static String getIncremental() {
        return Build.VERSION.INCREMENTAL;
    }

    /**
     * 获取标签
     * @return 标签
     */
    public static String getTags() {
        return Build.TAGS;
    }

    /**
     * 获取类型
     * @return String 类型
     */
    public static String getType() {
        return Build.TYPE;
    }

    /**
     * CORE-VER
     * 内核版本
     * return String
     */
    public static String getLinuxCoreVerison() {
        Process process = null;
        String kernelVersion = "";
        try {
            process = Runtime.getRuntime().exec("cat /proc/version");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // get the output line
        InputStream outs = process.getInputStream();
        InputStreamReader isrout = new InputStreamReader(outs);
        BufferedReader brout = new BufferedReader(isrout, 8 * 1024);

        String result = "";
        String line;
        try {
            while ((line = brout.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*try {
            if (result != "") {
                String Keyword = "version ";
                int index = result.indexOf(Keyword);
                line = result.substring(index + Keyword.length());
                index = line.indexOf(" ");
                kernelVersion = line.substring(0, index);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return kernelVersion;
        */
        return result;
    }

    /**
     * 获取系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static String getSystemRunTime(){
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        return  timeFormat.format(System.currentTimeMillis() - SystemClock.elapsedRealtimeNanos()/1000000);
    }

    /**
     * 获取当前时区
     * @return
     */
    public static String getCurrentTimeZone()
    {
        TimeZone tz = TimeZone.getDefault();
        return createGmtOffsetString(true,true,tz.getRawOffset());
    }

    public static String createGmtOffsetString(boolean includeGmt,
                                               boolean includeMinuteSeparator, int offsetMillis) {
        int offsetMinutes = offsetMillis / 60000;
        char sign = '+';
        if (offsetMinutes < 0) {
            sign = '-';
            offsetMinutes = -offsetMinutes;
        }
        StringBuilder builder = new StringBuilder(9);
        if (includeGmt) {
            builder.append("GMT");
        }
        builder.append(sign);
        appendNumber(builder, 2, offsetMinutes / 60);
        if (includeMinuteSeparator) {
            builder.append(':');
        }
        appendNumber(builder, 2, offsetMinutes % 60);
        return builder.toString();
    }

    private static void appendNumber(StringBuilder builder, int count, int value) {
        String string = Integer.toString(value);
        for (int i = 0; i < count - string.length(); i++) {
            builder.append('0');
        }
        builder.append(string);
    }


}
