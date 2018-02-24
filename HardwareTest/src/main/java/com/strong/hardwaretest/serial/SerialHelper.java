package com.strong.hardwaretest.serial;

import android.util.Log;

import com.strong.hardwaretest.entity.ComBean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

/**
 * 串口辅助抽象类
 * 提供一些串口的初始化工作和处理串口数据的方法
 * 其中包含一个读取数据的线程 如果涉及到发送指令(血压)的操作 直接调send方法即可
 */
public abstract class SerialHelper {

    private SerialPort mSerialPort;              //串口类对象
    private OutputStream mOutputStream;          //输出流对象
    private InputStream mInputStream;            //输入流对象
    private ReadThread mReadThread;             //读取线程对象
    private String sPort = "/dev/ttymxc2";      //串口地址
    private int iBaudRate = 4800;               //波特率
    private boolean _isOpen = false;            //串口是否打开的标志位
    private int iReadDelay = 10;                //读取数据线程的延迟时间
    private boolean readThreadFlag;             //读取数据线程的标志

    private int bufferSize;

    /**
     * 打开串口（血氧、心电校验位isOxy为1，其他测量模块都为0）
     * @param isOxy
     * @throws SecurityException
     * @throws IOException
     * @throws InvalidParameterException
     */
    public void open(int isOxy) throws SecurityException, IOException, InvalidParameterException {
        mSerialPort = new SerialPort(new File(sPort), iBaudRate, 0, isOxy);  //实例化串口对象
        mOutputStream = mSerialPort.getOutputStream();                       //获取串口输出流
        mInputStream = mSerialPort.getInputStream();                         //获取串口输入流
        mReadThread = new ReadThread();                                      //创建读取数据线程的对象
        readThreadFlag = true;                                               //打开读取线程标志位
        mReadThread.start();                                                 //开启读取数据的线程
        _isOpen = true;                                                      //设置是否打开串口的标志位true
    }

    /**
     * 关闭串口流操作
     */
    public void closeStream() {
        readThreadFlag = false;  //将读取线程设置结束循环
        if (mInputStream != null) {
            try {
                mInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (mOutputStream != null) {
            try {
                mOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        _isOpen = false;
    }

    //设置读串口数据标志
    public void setReadThreadFlag(boolean flag){
        readThreadFlag = flag;
    }


    //关闭串口的方法(目前有bug，需要修改close方法)
    public void close(){
        if (mReadThread != null) {
            readThreadFlag = false;  //将读取线程设置结束循环
            mReadThread = null;      //将读取线程置成null
        }
        if (mSerialPort != null) {
            SerialPort.close();      //关闭串口
            mSerialPort = null;      //将串口设置成空
        }
        _isOpen = false;
    }

    //发送指令的方法
    public void send(byte[] bOutArray) {
        try {
            mOutputStream.write(bOutArray);    //将字节数组写到输出流对象中去
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //发送一个字节
    public void send(byte bOutArray) {
        try {
            mOutputStream.write(bOutArray);
            Log.d("SerialHelper", "write bytes succeed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //读取数据的线程，将读取到的字节数据封装到实体类中
    private class ReadThread extends Thread {
        @Override
        public synchronized void run() {
            byte[] buffer = new byte[bufferSize];  //创建一个接收字节数据的缓冲数组
            int size;
            while (readThreadFlag) {
                synchronized (this) {
                    try {
                        if (mInputStream == null) return;
                        size = mInputStream.read(buffer); //每一次读取数据的大小
                        if (size > 0) {
                            //将接收到字节数组封装成一个ComBean对象
                            ComBean comRecData = new ComBean(buffer, size);
                            //调用接收封装类的方法
                            onDataReceived(comRecData);
                            comRecData = null;
                        }
                        Thread.sleep(iReadDelay);
                    } catch (Exception e) {
                        Log.e("SerialHelper", "serial io exception:" + e.getMessage());
                        return;
                    }
                }
            }
        }
    }

    //设置波特率
    public boolean setBaudRate(int iBaud) {
        if (_isOpen) {
            return false;
        } else {
            iBaudRate = iBaud;
            return true;
        }
    }


    //设置串口地址
    public boolean setPort(String sPort) {
        if (_isOpen) {
            return false;
        } else {
            this.sPort = sPort;
            return true;
        }
    }


    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    //判断串口是否打开
    public boolean isOpen() {
        return _isOpen;
    }

    /*设置读取帧的线程休眠时间*/
    public void setiReadDelay(int iReadDelay) {
        this.iReadDelay = iReadDelay;
    }

    //接收数据的抽象方法，参数为一个数据封装类
    protected abstract void onDataReceived(ComBean ComRecData);

}