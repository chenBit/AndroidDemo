package com.chenbit.demo.download;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.chenbit.demo.app.DemoApp;

public class DownloadManager implements DownloadService.DownloadListener {
    private static final String TAG = DownloadManager.class.getSimpleName();
    private static DownloadManager mInstance;
    private OnUIDownloadListener mListener;
    private Context mContext;

    public static void init() {
        getInstance();
    }

    public interface OnUIDownloadListener {
        void onProgress(String tag, int progress);

        void onStatus(String tag, int status);
    }

    private DownloadManager() {
        mContext = DemoApp.getAppContext();
        bindDownloadService();
    }

    public void setUIDownloadListener(OnUIDownloadListener listener) {
        mListener = listener;
    }

    public static DownloadManager getInstance() {
        if (mInstance == null) {
            synchronized (DownloadManager.class) {
                if (mInstance == null) {
                    mInstance = new DownloadManager();
                }
            }
        }
        return mInstance;
    }

    public void bindDownloadService() {
        Intent intent = new Intent(mContext, DownloadService.class);
        mContext.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                DownloadService.DownloadBinder binder = (DownloadService.DownloadBinder) service;
                binder.setDownloadListener(DownloadManager.this);
                Log.e(TAG, "===onServiceConnected===");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.e(TAG, "===onServiceDisconnected===");
            }
        }, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onProgress(String tag, int progress) {
        if (mListener != null) {
            mListener.onProgress(tag, progress);
        }
    }

    @Override
    public void onStatus(String tag, int status) {
        if (mListener != null) {
            mListener.onStatus(tag, status);
        }
    }
}
