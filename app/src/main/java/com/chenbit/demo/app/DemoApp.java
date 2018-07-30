package com.chenbit.demo.app;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.chenbit.demo.R;
import com.chenbit.demo.download.DownloadManager;
import com.cw.androidbase.sdk.app.BaseApp;
import com.cw.androidbase.sdk.ui.config.UIConfiguration;
import com.nationsky.downloadersdk.config.NQDownloadConfiguration;
import com.nationsky.downloadersdk.core.NQDownloadManager;

public class DemoApp extends BaseApp {
    private static final String TAG = DemoApp.class.getSimpleName();

    private void initDownloader() {
        NQDownloadConfiguration configuration = new NQDownloadConfiguration();
        configuration.setMaxThreadNum(9); //线程池总的线程个数，除以Thread Num得到最多有几个任务同时进行下载
        configuration.setThreadNum(3); //单个任务分成几个线程下载
        NQDownloadManager.getInstance().init(this, configuration);
        DownloadManager.init();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void init() {
        initDownloader();
        UIConfiguration.setThemeColor(R.color.colorPrimary);
    }
}
