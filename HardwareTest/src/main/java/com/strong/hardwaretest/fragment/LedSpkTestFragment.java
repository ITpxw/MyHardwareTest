package com.strong.hardwaretest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.strong.hardwaretest.R;
import com.strong.hardwaretest.utils.AlarmLedTestUtil;
import com.strong.hardwaretest.utils.SpkUtil;
import com.strong.hardwaretest.view.TitleBarView;

/**
 * 基本信息
 */
public class LedSpkTestFragment extends Fragment {

    private Context mContext;
    private View mBaseView;
    private Button btnLedsTest,btnSpkTest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        mBaseView = inflater.inflate(R.layout.fragment_ledspktest, null);
        findView();
        init();

        return mBaseView;
    }

    private void findView() {

        btnLedsTest = (Button) mBaseView.findViewById(R.id.btn_ledstest);
        btnSpkTest = (Button) mBaseView.findViewById(R.id.btn_spktest);
    }

    private void init() {
        //LED报警灯
        final AlarmLedTestUtil alarmLedTestUtil = new AlarmLedTestUtil();
        //扬声器
        final SpkUtil spkUtil = new SpkUtil(mContext);

        btnLedsTest.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                alarmLedTestUtil.TestLed1();
                alarmLedTestUtil.TestLed2();
            }
        });

        btnSpkTest.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                spkUtil.playSoundTest(1);
            }
        });

    }

}
