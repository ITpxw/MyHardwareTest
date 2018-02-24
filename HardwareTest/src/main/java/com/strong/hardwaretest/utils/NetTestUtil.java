package com.strong.hardwaretest.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.TextView;

import com.strong.hardwaretest.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import static java.lang.Thread.sleep;

/**
 * Created by pxw on 2018/1/4.
 */

public class NetTestUtil {

    private static int currVolume = 0;

    /** 打开扬声器 */
    public static boolean OpenSpeaker(Context context)
    {
        try
        {
            AudioManager localAudioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
            currVolume = localAudioManager.getStreamVolume(0);
            if (!localAudioManager.isSpeakerphoneOn())
            {
                localAudioManager.setSpeakerphoneOn(true);
                localAudioManager.setStreamVolume(0, localAudioManager.getStreamMaxVolume(0), 0);
            }
            return true;
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
        return false;
    }

    /** 检测扬声器 */
    public static boolean isSpeakerGood(Context context)
    {
        return OpenSpeaker(context);
    }


    /** 检测蓝牙 */
    public static boolean bluetoothIsGood()
    {
        BluetoothAdapter localBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!localBluetoothAdapter.isEnabled())
        {
            boolean status = localBluetoothAdapter.enable();
            localBluetoothAdapter.disable();
            return status;
        }

        boolean status = localBluetoothAdapter.disable();
        localBluetoothAdapter.enable();
        return status;
    }

    /** 检测Wifi */
    public static boolean isWifiIsGood(Context context)
    {
        WifiManager localWifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        if (!localWifiManager.isWifiEnabled())
        {
            boolean status = localWifiManager.setWifiEnabled(true);
            localWifiManager.setWifiEnabled(false);
            return status;
        }
        boolean status = localWifiManager.setWifiEnabled(false);
        localWifiManager.setWifiEnabled(true);
        return status;
    }

    /**
     * 没有连接网络
     */
    private static final String NETWORK_NONE = "无网络";
    /**
     * 移动网络
     */
    private static final String NETWORK_MOBILE = "移动网络";
    /**
     * 无线网络
     */
    private static final String NETWORK_WIFI = "WIFI网络";
    /**
     * 以太网络
     */
    private static final String NETWORK_ETHERNET = "以太网";

    public static String getNetWorkState(Context context) {
        // 得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {

            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NETWORK_WIFI;
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NETWORK_MOBILE;
            }else if(activeNetworkInfo.getType() == (ConnectivityManager.TYPE_ETHERNET)){
                return NETWORK_ETHERNET;
            }
        } else {
            return NETWORK_NONE;
        }
        return NETWORK_NONE;
    }


    public static String getUsbDeviceInfo(Context context){
        UsbManager manager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();

        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

        while(deviceIterator.hasNext()){

            UsbDevice device = deviceIterator.next();
            device.getDeviceName();
            Log.i("------", "getUsbDeviceInfo: "+device.toString());
            Log.i("------", "getUsbDeviceInfo: "+device.getDeviceName());
            Log.i("------", "getUsbDeviceInfo: "+device.getProductId()+"--"+device.getVendorId());
            Log.i("------", "getUsbDeviceInfo: "+device.describeContents());
            Log.i("------", "getUsbDeviceInfo: "+device.getDeviceProtocol());
            Log.i("------", "getUsbDeviceInfo: "+device.getDeviceClass()+" | "+device.getDeviceId()+" | "+device.getDeviceSubclass());

        }

        return null;
    }

}
