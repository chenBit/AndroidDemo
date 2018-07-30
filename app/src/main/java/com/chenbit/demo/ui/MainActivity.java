package com.chenbit.demo.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chenbit.demo.R;
import com.chenbit.demo.download.DownloadActivity;
import com.cw.androidbase.sdk.receiver.NetworkChangeReceiver;
import com.cw.androidbase.sdk.ui.activity.BaseActivity;
import com.cw.androidbase.sdk.utils.AssetUtil;
import com.tdtech.devicemanager.DevicePolicyManager;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends BaseActivity implements NetworkChangeReceiver.NetEventListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean isRestrict;

    @Override
    protected int initContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        Button btnDownload = findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DownloadActivity.class);
                startActivity(intent);
            }
        });
        Button btnTest = findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DevicePolicyManager dpm = DevicePolicyManager.getInstance(MainActivity.this);
                Log.e(TAG, "=====isRestrict======" + isRestrict);
                dpm.getTelephonyPolicy().isAllowForPhoneNumberIntercepted(isRestrict);
                isRestrict = !isRestrict;
                dpm.getTelephonyPolicy().insertPhoneNumber("10010");
            }
        });
    }

    @Override
    protected void initData() {

    }

    private void set2Array() {
        Set set = new HashSet();
        set.add(1);
        set.add(2);
        set.add(3);
        Integer[] iArray = new Integer[set.size()];
        set.toArray(iArray);
        for (Integer integer : iArray) {
            Log.e(TAG, "==integer==" + integer);
        }
    }

    private void testAssetConfig() {
        String config = AssetUtil.getAssetConfig(this, "config.txt");
        if (!TextUtils.isEmpty(config)) {
            String[] split = config.split("@");
            Log.e(TAG, "==split length==" + split.length);
            if (split.length > 1) {
                String activeIpInfo = split[0];
                String[] split1 = activeIpInfo.split(":");
                String activeIp = split1[1];
                Log.e(TAG, "==activeIp==" + activeIp);
                String verifyIpInfo = split[1];
                String[] split2 = verifyIpInfo.split(":");
                String verifyIp = split2[1];
                Log.e(TAG, "==verifyIp==" + verifyIp);
            }
        }
        Log.e(TAG, "==config==" + config);
    }

    public static void showAlert(Context context, String title_res, String msg_res) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(title_res)
                .setMessage(msg_res).setIcon(android.R.drawable.ic_dialog_info);
        builder.create();
        builder.setCancelable(true);
        builder.show();
    }

    @Override
    public void onNetChange(int netWorkState) {
        Log.e(TAG, "===onNetChange===" + netWorkState);
        showToast("网络发生变化了：" + netWorkState);
    }
}
