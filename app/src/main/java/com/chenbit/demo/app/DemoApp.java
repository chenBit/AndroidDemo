package com.chenbit.demo.app;

import android.os.Build;
import android.os.Looper;
import android.support.annotation.RequiresApi;

import com.chenbit.demo.R;
import com.chenbit.demo.db.DBConstants;
import com.chenbit.demo.download.DownloadManager;
import com.chenbit.demo.test.hook.Hooker;
import com.cw.androidbase.sdk.app.BaseApp;
import com.cw.androidbase.sdk.db.ITable;
import com.cw.androidbase.sdk.utils.SSLUtil;
import com.nationsky.downloadersdk.config.NQDownloadConfiguration;
import com.nationsky.downloadersdk.core.NQDownloadManager;

import java.util.List;

public class DemoApp extends BaseApp {
    private static final String TAG = DemoApp.class.getSimpleName();

    private static final String SP_NAME = "cw_demo_sp";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void init() {
        initDownloader();
        initHooker();
    }

    private void initDownloader() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NQDownloadConfiguration configuration = new NQDownloadConfiguration();
                configuration.setMaxThreadNum(9); //线程池总的线程个数，除以Thread Num得到最多有几个任务同时进行下载
                configuration.setThreadNum(3); //单个任务分成几个线程下载
                NQDownloadManager.getInstance().init(mAppContext, configuration);
                SSLUtil.verify();
                DownloadManager.init();
            }
        }).start();
    }

    private void initHooker() {
        try {
//            Hooker.hookInstrumention();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int initUIThemeColor() {
        return R.color.colorAccent;
    }

    @Override
    public int initDBVersion() {
        return DBConstants.DB_VERSION;
    }

    @Override
    public String initDBName() {
        return DBConstants.DB_NAME;
    }

    @Override
    public List<Class<? extends ITable>> getAllTables() {
        return null;
    }

    @Override
    public String initSPName() {
        return SP_NAME;
    }

}
