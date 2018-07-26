package com.example.nsky.studydemo.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.nsky.studydemo.download.DownloadManager;
import com.example.nsky.studydemo.receiver.NetworkChangeReceiver;
import com.nationsky.downloadersdk.config.NQDownloadConfiguration;
import com.nationsky.downloadersdk.core.NQDownloadManager;

import java.util.ArrayList;
import java.util.List;

public class DemoApp extends Application {
    private static final String TAG = DemoApp.class.getSimpleName();
    private static Context mAppContext;
    private static List<NetworkChangeReceiver.NetEventListener> mNetListenerList;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"====application is start running====");
        mAppContext = this;
        initDownloader();
        mNetListenerList = new ArrayList<>();
    }

    private void initDownloader() {
        NQDownloadConfiguration configuration = new NQDownloadConfiguration();
        configuration.setMaxThreadNum(9); //线程池总的线程个数，除以Thread Num得到最多有几个任务同时进行下载
        configuration.setThreadNum(3); //单个任务分成几个线程下载
        NQDownloadManager.getInstance().init(this, configuration);
        DownloadManager.init();
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    public static List<NetworkChangeReceiver.NetEventListener> getNetListenerList() {
        return mNetListenerList;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.e(TAG,"====application is terminated====");
    }
}
