package com.cw.androidbase.sdk.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.cw.androidbase.sdk.config.IBaseConfig;
import com.cw.androidbase.sdk.receiver.NetworkChangeReceiver;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseApp extends Application implements IBaseConfig{
    private static final String TAG = BaseApp.class.getSimpleName();
    protected static Context mAppContext;
    protected static BaseApp mInstance;
    private static List<NetworkChangeReceiver.NetEventListener> mNetListenerList;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "====application is start running====");
        mInstance = this;
        mAppContext = getApplicationContext();
        mNetListenerList = new ArrayList<>();
        init();
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    public static BaseApp getInstance(){
        return mInstance;
    }

    public static List<NetworkChangeReceiver.NetEventListener> getNetListenerList() {
        return mNetListenerList;
    }

    protected abstract void init();

}