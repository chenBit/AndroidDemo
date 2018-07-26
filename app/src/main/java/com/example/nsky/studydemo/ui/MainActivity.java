package com.example.nsky.studydemo.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.nsky.studydemo.MediaContentObserver;
import com.example.nsky.studydemo.R;
import com.example.nsky.studydemo.download.DownloadActivity;
import com.example.nsky.studydemo.utils.AssetUtil;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MediaContentObserver mScreenObserver;
    private Handler mHandler = new Handler();

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnDownload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DownloadActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btnTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    private void listenDB() {
        mScreenObserver = new MediaContentObserver(MainActivity.this, mHandler);
        registerContentObserver();
    }

    public static void showAlert(Context context, String title_res, String msg_res) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(title_res)
                .setMessage(msg_res).setIcon(android.R.drawable.ic_dialog_info);
        builder.create();
        builder.setCancelable(true);
        builder.show();
    }

    private void registerContentObserver() {
        Uri videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        getContentResolver().registerContentObserver(videoUri, false, mScreenObserver);
        getContentResolver().registerContentObserver(imageUri, false, mScreenObserver);
        Log.i(TAG, "registered!---------------------------");
    }

    @Override
    public void onNetChange(int netWorkState) {
        Log.e(TAG, "===onNetChange===" + netWorkState);
        showToast("网络发生变化了：" + netWorkState);
    }
}
