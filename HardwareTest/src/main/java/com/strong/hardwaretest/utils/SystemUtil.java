package com.strong.hardwaretest.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Locale;


/**
 * 系统工具类
 * 获取系统信息
 */
public class SystemUtil {

    private static final String MEM_INFO_PATH = "/proc/meminfo";


    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }



    /**
     * 获取制造商信息
     * @return 制造商信息
     */
    public static String getManufacturer() {
        return android.os.Build.MANUFACTURER;
    }

    /**
     * 获取机型信息
     * @return  机型信息
     */
    public static String getModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取品牌信息
     *
     * @return  品牌信息
     */
    public static String getBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取主板信息
     * @return 主板信息
     */
    public static String getBoard() {
        return android.os.Build.BOARD;
    }

    /**
     * 获取设备信息
     * @return 设备信息
     */
    public static String getDevice() {
        return android.os.Build.DEVICE;
    }

    /**
     * 获取硬件信息
     * @return 硬件信息
     */
    public static String getHardware() {
        return android.os.Build.HARDWARE;
    }

    /**
     * 获取产品信息
     * @return 产品信息
     */
    public static String getProduct() {
        return android.os.Build.PRODUCT;
    }

    /**
     * 获取系统序列号
     * @return
     */
    public static String getSerialNumber(){
        String serial = null;
        try {
            Class<?> c =Class.forName("android.os.SystemProperties");
            Method get =c.getMethod("get", String.class);
            serial = (String)get.invoke(c, "ro.serialno");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }

    /**
     * 获取系统已安装内存
     * @param context
     * @return
     */
    public static String getTotalRam(Context context){//GB
        String firstLine = null;
        int totalRam = 0 ;

        try{
            FileReader fileReader = new FileReader(MEM_INFO_PATH);
            BufferedReader br = new BufferedReader(fileReader,8192);
            firstLine = br.readLine().split("\\s+")[1];
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(firstLine != null){
            totalRam = (int)Math.ceil((new Float(Float.valueOf(firstLine) / (1024 * 1024)).doubleValue()));
        }

        return totalRam + "GB";//返回1GB/2GB/3GB/4GB
    }

    /**
     * 获取系统总内存大小
     *
     * @param context
     * @return
     */
    public static String getTotalMemory(Context context) {
        return getMemInfoIype(context, "MemTotal");
    }

    /**
     * 获取系统可用内存大小
     *
     * @param context
     * @return
     */
    /*public static String getAvailableMemory(Context context) {
        return getMemInfoIype(context, "MemFree");
    }*/

    public static String getAvailableMemory(Context context) {
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化;
    }


    private static String getMemInfoIype(Context context, String type) {// 获取android当前可用内存大小
        try {
            FileReader fileReader = new FileReader(MEM_INFO_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader, 4 * 1024);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                if (str.contains(type)) {
                    break;
                }
            }
            bufferedReader.close();
            /* \\s表示   空格,回车,换行等空白符,
            +号表示一个或多个的意思     */
            String[] array = str.split("\\s+");
            // 获得系统总内存，单位是KB，乘以1024转换为Byte
            int length = Integer.valueOf(array[1]).intValue() * 1024;
            return Formatter.formatFileSize(context, length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 得到内置存储空间的总容量
     * @param context
     * @return
     */
    public static String getStorageToatalSpace(Context context){
        String path = Environment.getDataDirectory().getPath();

        StatFs statFs = new StatFs(path);
        long blockSize = statFs.getBlockSize();
        long totalBlocks = statFs.getBlockCount();

        long totalRom_length = totalBlocks*blockSize;

        return Formatter.formatFileSize(context,totalRom_length);
    }

    /**
     * 得到内置存储空间的可用容量
     * @param context
     * @return
     */
    public static String getStorageAvailableSpace(Context context){
        String path = Environment.getDataDirectory().getPath();

        StatFs statFs = new StatFs(path);
        long blockSize = statFs.getBlockSize();
        long availableBlocks = statFs.getAvailableBlocks();

        long availableRom_length = availableBlocks*blockSize;

        return Formatter.formatFileSize(context,availableRom_length);
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return  手机IMEI
     */
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getDeviceId();
        }
        return null;
    }

    /**
     * BASEBAND-VER
     * 基带版本
     * return String
     */
    public static String getBasebandVerison(){
        String Version = "";
        try {
            Class cl = Class.forName("android.os.SystemProperties");
            Object invoker = cl.newInstance();
            Method m = cl.getMethod("get", new Class[] { String.class,String.class });
            Object result = m.invoke(invoker, new Object[]{"gsm.version.baseband", "no message"});

            Version = (String)result;
        } catch (Exception e) {
        }
        return Version;
    }

}
