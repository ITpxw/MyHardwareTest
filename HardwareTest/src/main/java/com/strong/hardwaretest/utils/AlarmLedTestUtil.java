package com.strong.hardwaretest.utils;

/**
 * Created by pxw on 2018/1/5.
 * 报警灯的测试
 */

public class AlarmLedTestUtil {
    static {
        System.loadLibrary("HardwareTest");
    }

    private native int ledCtrl(int which, int status);

    private native int ledOpen();
    private native void ledClose();

    private int isOk;

    public AlarmLedTestUtil() {
        isOk = ledOpen();
    }

    //黄灯测试
    public void TestLed1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isOk >= 0) {
                    ledCtrl(0, 1);
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    ledCtrl(0, 0);
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    //红灯测试
    public void TestLed2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isOk >= 0) {
                    ledCtrl(1, 1);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    ledCtrl(1, 0);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    public void CloseLeds(){
        ledClose();
    }

}
