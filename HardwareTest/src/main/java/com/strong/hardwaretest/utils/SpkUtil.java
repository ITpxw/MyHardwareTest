package com.strong.hardwaretest.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.widget.Toast;

import com.strong.hardwaretest.R;
import com.strong.hardwaretest.application.SysApplication;

import java.util.HashMap;


public class SpkUtil {

    private Context context;
    private SoundPool soundPool;
    private HashMap<Integer, Integer> soundMap;
    float leftVolume = 1.0f, rightVolume = 1.0f;
    int priority = 1, loop = 0;
    float rate = 1.0f;          //正常速率

    public SpkUtil(Context context){
        this.context = context;
        // 第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 20);
        soundMap = new HashMap<Integer, Integer>();
        soundMap.put(1, soundPool.load(context, R.raw.beep, 1));
        soundMap.put(2, soundPool.load(context, R.raw.procyon, 1));
        soundMap.put(3, soundPool.load(context, R.raw.heart_beat, 1));
    }

    public void playSoundTest(int SoundId) {
        String result = "";
        result += "--------------------------------------------------------------------------------------------------------------------------\n";
        result += "扬声器测试结果：\n";
        int streamID = soundPool.play(soundMap.get(SoundId), leftVolume, rightVolume, priority, loop, rate);
        if (streamID == 0) {
            //播放失败
            result += "\t"+"failed" + "\n";
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
        } else {
            //播放成功
            result += "\t"+"ok" + "\n";
            Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
        }
        SysApplication.getInstance().setLedspkInfo(result);

    }
}
