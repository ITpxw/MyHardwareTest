package com.strong.hardwaretest.fragment;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.strong.hardwaretest.R;
import com.strong.hardwaretest.application.SysApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import static android.content.Context.WIFI_SERVICE;

/**
 * 基本信息
 */
public class NetTestFragment extends Fragment {

    private Context mContext;
    private View mBaseView;
    private Button btnEthernet, btnWIFI, btnBluetooth;
    private Button btOpen, btSearch, btClose;
    private TextView tvNetInfo;

    private WifiManager wifiManager;        // 管理wifi
    private ArrayList<ScanResult> list;     // 存放周围wifi热点对象的列表
    private StringBuilder netInfo = new StringBuilder();

    //蓝牙
    BluetoothAdapter mBluetoothAdapter;
    boolean blueToothState = false;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // 如果找到了设备
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                // 判断配对，如果没有配对，就添加到列表
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    tvNetInfo.append("\n" + device.getName() + ":" + device.getAddress());
                    netInfo.append("\t" + device.getName() + ":" + device.getAddress() + "\n");
                }
                //如果搜索完成
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
                    .equals(action)) {
                tvNetInfo.append("\n" + "扫描完成");
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        mBaseView = inflater.inflate(R.layout.fragment_nettest, null);

        findView();
        init();

        return mBaseView;
    }

    private void findView() {
        btnEthernet = (Button) mBaseView.findViewById(R.id.btn_ethernet);
        btnWIFI = (Button) mBaseView.findViewById(R.id.btn_wifi);

        tvNetInfo = (TextView) mBaseView.findViewById(R.id.tv_netinfo);

        btOpen = (Button) mBaseView.findViewById(R.id.btn_bt_open);
        btSearch = (Button) mBaseView.findViewById(R.id.btn_bt_search);
        btClose = (Button) mBaseView.findViewById(R.id.btn_bt_close);
    }


    private void init() {

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //注册正在搜索蓝牙时发送的广播
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        mContext.registerReceiver(receiver, filter);

        //注册搜索完成后发送的广播
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        mContext.registerReceiver(receiver, filter);

        wifiManager = (WifiManager) mContext.getSystemService(WIFI_SERVICE); //获得系统wifi服务


        btnEthernet.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiManager.setWifiEnabled(false);
                //测试网络
                new NetPingTestUtil().execute("www.baidu.com");
            }
        });

        btnWIFI.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(true);
                } else {
                    Log.i("------", "WIFI已开启");
                }

                list = (ArrayList<ScanResult>) wifiManager.getScanResults();
                sortByLevel(list);
                showWifiList(list);
            }
        });


        btOpen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 判断蓝牙的状态
                blueToothState = mBluetoothAdapter.isEnabled();
                if (blueToothState) {
                    Toast.makeText(mContext, "蓝牙已经打开", Toast.LENGTH_SHORT).show();
                } else {
                    mBluetoothAdapter.enable();
                    Toast.makeText(mContext, "正在打开..", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                netInfo.append("--------------------------------------------------------------------------------------------------------------------------" + "\n");
                netInfo.append("蓝牙测试结果：\n");
                // 判断蓝牙的状态
                blueToothState = mBluetoothAdapter.isEnabled();
                if (!blueToothState) {
                    mBluetoothAdapter.enable();
                    Toast.makeText(mContext, "正在打开.", Toast.LENGTH_SHORT).show();
                }

                // 如果正在搜索(扫描只能执行一次，如果蓝牙正在扫描，又执行了一次扫描，会报错)
                if (mBluetoothAdapter.isDiscovering()) {
                    // 就取消
                    mBluetoothAdapter.cancelDiscovery();
                }
                // 开启蓝牙搜索，此函数是运行在子线程中的
                mBluetoothAdapter.startDiscovery();
            }
        });

        btClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 判断蓝牙的状态
                blueToothState = mBluetoothAdapter.isEnabled();
                if (blueToothState) {
                    mBluetoothAdapter.disable();
                    Toast.makeText(mContext, "正在关闭.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "蓝牙已经关闭!", Toast.LENGTH_SHORT).show();
                }

                tvNetInfo.setText("");
                SysApplication.getInstance().setNetTestInfo(netInfo.toString());
            }
        });


    }

    /**
     * 依次展示周围wifi的ssid、bssid、capbilities、level
     *
     * @param list 存放周围wifi热点对象的列表
     *             0   —— (-55)dbm  满格(4格)信号
     *             (-55) —— (-70)dbm  3格信号
     *             (-70) —— (-85)dbm　2格信号
     *             (-85) —— (-100)dbm 1格信号
     */
    public void showWifiList(ArrayList<ScanResult> list) {

        int numLevel1 = 0;
        int numLevel2 = 0;
        int numLevel3 = 0;
        int numLevel4 = 0;

        tvNetInfo.setText("");
        tvNetInfo.append("--------------------------------------------------------------------------------------------------------------------------" + "\n");
        tvNetInfo.append("WIFI测试结果：\n");
        tvNetInfo.append("wifi个数：" + list.size());

        for (int i = 0; i < list.size(); i++) {
            String strSsid = list.get(i).SSID;
            String strBssid = list.get(i).BSSID;
            String strCapabilities = list.get(i).capabilities;
            int strLevel = list.get(i).level;
            int level = 0;
            if (strLevel >= -55 && strLevel <= 0) {
                level = 4;
                numLevel4++;
            } else if (strLevel >= -70 && strLevel < -55) {
                level = 3;
                numLevel3++;
            } else if (strLevel >= -85 && strLevel < -70) {
                level = 2;
                numLevel2++;
            } else if (strLevel >= -100 && strLevel < -85) {
                level = 1;
                numLevel1++;
            } else {
                level = 0;
            }

            Log.i("----", "信号" + (i + 1) + "\n" + "SSID: " + strSsid + "\n" + "BSSID: " + strBssid + "\n" + "capabilities： "
                    + strCapabilities + "\n" + "level: " + strLevel + "\n");
            /*tvWifi.append("\n" + "信号" + (i + 1) + "\n" + "SSID: " + strSsid + "\n" + "BSSID: " + strBssid + "\n" + "capabilities： "
                    + strCapabilities + "\n" + "level: " + strLevel + "(" + level + ")" + "\n");*/
        }

        tvNetInfo.append("\n信号级别个数：\n");
        tvNetInfo.append("\t1格信号：" + numLevel1 + "\n");
        tvNetInfo.append("\t2格信号：" + numLevel2 + "\n");
        tvNetInfo.append("\t3格信号：" + numLevel3 + "\n");
        tvNetInfo.append("\t4格信号：" + numLevel4 + "\n");

        if (list.size() > 0) {
            netInfo.append("--------------------------------------------------------------------------------------------------------------------------" + "\n");
            netInfo.append("WIFI测试结果：\n");
            netInfo.append("wifi个数：" + list.size());

            netInfo.append("\n信号级别个数：\n"
                    + "\t1格信号：" + numLevel1 + "\n"
                    + "\t2格信号：" + numLevel2 + "\n"
                    + "\t3格信号：" + numLevel3 + "\n"
                    + "\t4格信号：" + numLevel4 + "\n");
        }
        //SysApplication.getInstance().setNetTestInfo(tvNetInfo.getText().toString() + "\n");
    }

    /**
     * 将搜索到的wifi根据信号强度从强到时弱进行排序
     *
     * @param list 存放周围wifi热点对象的列表
     */
    public void sortByLevel(ArrayList<ScanResult> list) {

        Collections.sort(list, new Comparator<ScanResult>() {

            @Override
            public int compare(ScanResult lhs, ScanResult rhs) {
                return rhs.level - lhs.level;
            }
        });
    }

    public class NetPingTestUtil extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            tvNetInfo.setText("loading...");
            btnEthernet.setEnabled(false);
            btnEthernet.setText("测试中...");
            btnEthernet.setBackgroundColor(getResources().getColor(R.color.gray_font));
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            result = NetPing(params[0]);
            Log.i("ping", result);
            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            tvNetInfo.setText(result);
            btnEthernet.setEnabled(true);
            btnEthernet.setText("以太网测试");
            btnEthernet.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

        /**
         * 网络Ping测试
         *
         * @param str
         * @return
         */
        private String NetPing(String str) {
            String result = "";
            Process p;
            StringBuffer buffer = new StringBuffer();

            int byteCount = 0;

            try {
                //ping -c 3 -w 100  中  ，-c 是指ping的次数 3是指ping 3次 ，-w 100  以秒为单位指定超时间隔，是指超时时间为100秒
                p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + str);
                int status = p.waitFor();

                InputStream input = p.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(input));

                String line = "";
                while ((line = in.readLine()) != null) {
                    buffer.append(line);
                }

                Log.i("ping 返回数据", "result: " + buffer.toString());

                if (status == 0) {
                    result = "success";
                } else {
                    result = "failed";
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //SysApplication.getInstance().setNetTestInfo("\n" + buffer.toString() + "\n" + "以太网测试结果:" + result +"\n");
            netInfo.append("--------------------------------------------------------------------------------------------------------------------------");
            netInfo.append("\n" + "以太网测试结果:" + result + "\n" + buffer.toString() + "\n");
            return buffer.toString() + "\n" + result;
        }
    }

}
