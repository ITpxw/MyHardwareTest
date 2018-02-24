package com.strong.hardwaretest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.strong.hardwaretest.R;
import com.strong.hardwaretest.application.SysApplication;
import com.strong.hardwaretest.constant.ByteData;
import com.strong.hardwaretest.entity.ComBean;
import com.strong.hardwaretest.serial.SerialHelper;
import com.strong.hardwaretest.utils.DataUtil;
import com.strong.hardwaretest.view.TitleBarView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 基本信息
 */
public class InterfaceTestFragment extends Fragment {

    private Context mContext;
    private final int SHOW_BYTEDATA = 1;
    private final int SHOW_USBDATA = 2;
    private View mBaseView;
    private Button btnUsb, btnSpO2, btnNibp, btnEcg;
    private TextView tvData;

    private SerialControl serialControl;
    private DataQueueThread dispQueue;

    private StringBuilder interfaceInfo = new StringBuilder();

    private boolean isReceiveData = false;

    private boolean sendStatus;  //发送状态线程的标志
    int i = 0;

    private String rootPath = "/mnt/media_rw/udisk";        //U盘挂载目录

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SHOW_BYTEDATA) {
                Log.i("Data", "Data: " + (String) msg.obj);
                tvData.setText((String) msg.obj);
                interfaceInfo.append("--------------------------------------------------------------------------------------------------------------------------"+"\n");
                interfaceInfo.append("测试结果：\n");
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        mBaseView = inflater.inflate(R.layout.fragment_interfacetest, null);
        findView();
        init();

        return mBaseView;
    }

    private void findView() {
        btnUsb = (Button) mBaseView.findViewById(R.id.btn_usb);
        btnSpO2 = (Button) mBaseView.findViewById(R.id.btn_spo2);
        btnNibp = (Button) mBaseView.findViewById(R.id.btn_nibp);
        btnEcg = (Button) mBaseView.findViewById(R.id.btn_ecg);
        tvData = (TextView) mBaseView.findViewById(R.id.test_data);
    }


    private void init() {

        btnUsb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvData.setText("USB测试");
                int numFile = getFileDir(rootPath);
                tvData.setText("U盘根目录文件个数："+numFile);           //获取rootPath目录下的文件.
                interfaceInfo.append("--------------------------------------------------------------------------------------------------------------------------"+"\n");
                interfaceInfo.append("USB测试结果：\n");
                if(numFile >= 0 ){
                    interfaceInfo.append("\tUSB通讯正常\n");
                }

                SysApplication.getInstance().setInterfaceInfo(interfaceInfo.toString());
            }
        });

        btnSpO2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvData.setText("血氧测试");

                setPort("/dev/ttymxc3", 4800, 216, 16);         // 配置串口
                openComPort(serialControl, 1);                  // 打开串口
                if (!isReceiveData) {
                    isReceiveData = true;
                    dispQueue.start();                          // 开启数据接收线程
                } else {
                    isReceiveData = false;
                }
            }
        });


        btnNibp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvData.setText("血压测试");
                isReceiveData = true;

                setPort("/dev/ttymxc2", 4800, 128, 200);    // 配置串口
                openComPort(serialControl, 0);              // 打开串口

                dispQueue.start();                          // 开启数据接收线程


                if (!sendStatus) {

                    sendStatus = true;
                    new SendStatusThraed().start();    // 开启每秒给NIBP模块发送状态指令的线程
                    while (i < 3) {
                        i++;
                        serialControl.send(ByteData.pHand);
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        serialControl.send(ByteData.stop);
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    serialControl.send(ByteData.stop);
                    sendStatus = false;
                }
            }
        });

        btnEcg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvData.setText("心电测试");

                setPort("/dev/ttymxc1", 115200, 2048, 100);     // 配置串口
                openComPort(serialControl, 1);                  // 打开串口

                if (!isReceiveData) {
                    isReceiveData = true;
                    dispQueue.start();
                } else {
                    isReceiveData = false;
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            closeComPort(serialControl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        isReceiveData = false;
    }


    /**
     * 配置串口
     *
     * @param uartPort   端口号
     * @param baud       波特率
     * @param bufferSize 缓冲区大小
     * @param readDelay  读取延迟时间
     */
    private void setPort(String uartPort, int baud, int bufferSize, int readDelay) {
        serialControl = new SerialControl();        // 实例化串口控制类
        dispQueue = new DataQueueThread();          // 实例化接收数据的线程
        serialControl.setPort(uartPort);            // 设置端口
        serialControl.setBaudRate(baud);            // 设置波特率
        serialControl.setBufferSize(bufferSize);
        serialControl.setiReadDelay(readDelay);         // 设置读取数据线程的休眠时间

    }

    // SerialHelper的实现类（内部类）
    private class SerialControl extends SerialHelper {
        @Override
        protected void onDataReceived(final ComBean comBean) { // 重写父类的抽象方法
            dispQueue.AddQueue(comBean); // 向接收数据线程添加数据
        }
    }

    // 打开串口
    private void openComPort(SerialHelper serialHelper, int isOdd) {
        try {
            serialHelper.open(isOdd);
        } catch (Exception e) {
            Toast.makeText(mContext, "open port failed!", Toast.LENGTH_SHORT).show();
        }
    }

    // 关闭串口
    private void closeComPort(SerialHelper comPort) throws Exception {
        if (comPort != null) {
            comPort.closeStream();
        }
    }

    // 显示数据队列线程
    private class DataQueueThread extends Thread {
        private Queue<ComBean> QueueList = new LinkedBlockingDeque<ComBean>(); // 创建队列对象
        ComBean comData;

        @Override
        public void run() {
            while (isReceiveData) {

                if ((comData = QueueList.poll()) != null) {
                    Log.i("-----", DataUtil.bytes2HexString(comData.bRec));
                    Message msg = Message.obtain();
                    msg.what = SHOW_BYTEDATA;
                    msg.obj = DataUtil.bytes2HexString(comData.bRec);
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(10); // 睡眠100ms
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        synchronized void AddQueue(ComBean ComData) {
            QueueList.add(ComData); // 向队列中加数据
        }
    }

    /**
     * 开始测量后每秒给NIBP模块发送返回系统状态指令
     */
    private class SendStatusThraed extends Thread {
        @Override
        public void run() {
            while (sendStatus) {
                //发送返回系统状态指令
                serialControl.send(ByteData.pReturnSystemStatus);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getFileDir(String filePath) {
        try {
            Log.i("Udisk", "当前路径:" + filePath);// 设置当前所在路径  

            File f = new File(filePath);
            File[] files = f.listFiles();       // 列出所有文件

            Log.i("Udisk", "文件个数=" + files.length);

            return files.length;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return -1;
    }

}
