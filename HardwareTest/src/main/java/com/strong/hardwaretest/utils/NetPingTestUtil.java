package com.strong.hardwaretest.utils;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.strong.hardwaretest.application.SysApplication;
import com.strong.hardwaretest.fragment.NetTestFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by pxw on 2018/1/5.
 */

public class NetPingTestUtil extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... params) {
        String s = "";
        s = NetPing("www.baidu.com");
        Log.i("ping", s);
        return s;
    }

    /**
     * 网络Ping测试
     * @param str
     * @return
     */
    private String NetPing(String str) {
        String result = "";
        Process p;
        StringBuffer buffer = new StringBuffer();

        try {
            //ping -c 3 -w 100  中  ，-c 是指ping的次数 3是指ping 3次 ，-w 100  以秒为单位指定超时间隔，是指超时时间为100秒
            p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + str);
            int status = p.waitFor();

            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            //StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null){
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

        SysApplication.getInstance().setNetTestInfo("\n" + buffer.toString()+"\n"+"以太网测试结果:"+result);
        return result;
    }
}
