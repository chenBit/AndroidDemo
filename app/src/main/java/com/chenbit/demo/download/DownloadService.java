package com.chenbit.demo.download;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chenbit.demo.app.DemoApp;
import com.chenbit.demo.bean.AppInfo;
import com.cw.androidbase.sdk.receiver.NetworkChangeReceiver;
import com.cw.androidbase.sdk.utils.FileUtils;
import com.cw.androidbase.sdk.utils.NetworkUtils;
import com.nationsky.downloadersdk.core.NQDownloadManager;
import com.nationsky.downloadersdk.core.NQDownloadRequest;
import com.nationsky.downloadersdk.exception.DownloadException;
import com.nationsky.downloadersdk.iface.INQDownloadCallBack;

import java.io.File;
import java.util.HashSet;

public class DownloadService extends Service implements NetworkChangeReceiver.NetEventListener {
    private static final String TAG = DownloadService.class.getSimpleName();
    private static final String DATA_DOWNLOAD_DIR_NAME = "download";
    private static final String DEFAULT_DOWNLOAD_DIR = "AndroidDemo/Download";
    private NQDownloadManager mDownloadManager;
    private File mTempDownloadDir; //data/data/pkg name/app_download目录
    private File mRealDonloadDir;
    public static final int STATUS_DOWNLOADING = 100;
    public static final int STATUS_PAUSE = 101;
    public static final int STATUS_COMPLETED = 102;
    public static final String DATA_DOWNLOAD_INFO = "data_download_info";
    public static final String ACTION_DOWNLOAD = "action_download";
    public static final String ACTION_PAUSE = "action_pause";
    private DownloadListener mDownloadListener;
    private Handler mHandler;
    private HashSet<AppInfo> mReDownloadSet;

    @Override
    public void onNetChange(int netStatus) {
        Log.e(TAG, "===onNetChange===" + netStatus);
        if (NetworkUtils.NETWORK_NONE != netStatus) {
            reDownloadOnNetOK();
        }
    }

    interface DownloadListener {
        void onProgress(String tag, int progress);

        void onStatus(String tag, int status);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "===onBind===");
        return new DownloadBinder();
    }

    class DownloadBinder extends Binder {
        public void setDownloadListener(DownloadListener listener) {
            Log.e(TAG, "===setDownloadListener===");
            mDownloadListener = listener;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "===onCreate===");
        DemoApp.getNetListenerList().add(this);
        mDownloadManager = NQDownloadManager.getInstance();
        mTempDownloadDir = getDir(DATA_DOWNLOAD_DIR_NAME, Context.MODE_PRIVATE);
        mRealDonloadDir = new File(Environment.getExternalStorageDirectory(), DEFAULT_DOWNLOAD_DIR);
        mReDownloadSet = new HashSet<>();
        mHandler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "===onStartCommand===");
        String action = intent.getAction();
        AppInfo appInfo = (AppInfo) intent.getSerializableExtra(DATA_DOWNLOAD_INFO);
        if (appInfo == null) {
            return START_STICKY;
        }
        if (ACTION_DOWNLOAD.equalsIgnoreCase(action)) {
            startDownload(appInfo);
        } else if (ACTION_PAUSE.equalsIgnoreCase(action)) {
            mDownloadManager.pause(appInfo.url);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "===onDestroy===");
        DemoApp.getNetListenerList().remove(this);
        if (mDownloadManager != null) {
            mDownloadManager.pauseAll();
        }
    }

    private void startDownload(final AppInfo appInfo) {
        if (mDownloadManager == null || appInfo == null) {
            return;
        }
        if (!NetworkUtils.isNetworkOK()) {
            Log.e(TAG, "===startDownload===no network===do nothing===");
            return;
        }
        String url = appInfo.url;
        boolean isRunning = mDownloadManager.isRunning(url);
        if (isRunning) {
            mDownloadManager.pause(url);
            return;
        }
        NQDownloadRequest request = new NQDownloadRequest.Builder()
                .setFolder(mTempDownloadDir).setName(appInfo.name)
                .setUri(url).build();
        mDownloadManager.download(request, appInfo.url, new INQDownloadCallBack() {
            @Override
            public void onStarted() {
                Log.e(TAG, "==onStarted==");
                mDownloadListener.onStatus(appInfo.url, STATUS_DOWNLOADING);
            }

            @Override
            public void onProgress(long finished, long total, int progress) {
                Log.e(TAG, "finished: " + finished + "   total: " + total + "   progress: " + progress);
                mDownloadListener.onProgress(appInfo.url, progress);
            }

            @Override
            public void onCompleted() {
                Log.e(TAG, "==onCompleted==");
                mDownloadListener.onProgress(appInfo.url, 100);
                String fileName = appInfo.name;
                moveAndInstallApp(fileName);
            }

            @Override
            public void onPaused() {
                Log.e(TAG, "==onPaused==");
                mDownloadListener.onStatus(appInfo.url, STATUS_PAUSE);
            }

            @Override
            public void onCanceled() {
                Log.e(TAG, "==onCanceled==");
            }

            @Override
            public void onFailed(DownloadException e) {
                Log.e(TAG, "==onFailed==" + e);
                addFailedToList(appInfo);
                reDownloadOnFailed(appInfo);
            }
        });
    }

    private void addFailedToList(AppInfo appInfo) {
        Log.d(TAG, "======addFailedToList=======" + appInfo.name + "==contains: " + (mReDownloadSet == null ? "false" : mReDownloadSet.contains(appInfo)));
        if (mReDownloadSet == null) {
            return;
        }
        if (mReDownloadSet.contains(appInfo)) {
            return;
        }
        mReDownloadSet.add(appInfo);
    }

    private void reDownloadOnNetOK() {
        if (mReDownloadSet == null || mReDownloadSet.size() == 0) {
            return;
        }
        for (AppInfo appInfo : mReDownloadSet) {
            startDownload(appInfo);
        }
        mReDownloadSet.clear();
    }

    private void reDownloadOnFailed(AppInfo appInfo) {
        Log.d(TAG, "======reDownloadOnFailed=======10s后尝试重新下载==pkg name:" + appInfo.name);
        ReDownloadTask redownloadTask = new ReDownloadTask(appInfo);
        mHandler.postDelayed(redownloadTask, 10 * 1000);
    }

    class ReDownloadTask implements Runnable {

        private AppInfo appInfo;

        ReDownloadTask(AppInfo appInfo) {
            this.appInfo = appInfo;
        }

        @Override
        public void run() {
            Log.d(TAG, "======ReDownloadTask=======run start===pkg name: " + appInfo.name);
            if (NetworkUtils.isNetworkOK() && mReDownloadSet.contains(appInfo)) {
                mReDownloadSet.remove(appInfo);
                Log.d(TAG, "======ReDownloadTask=======start reDownload");
                startDownload(appInfo);
            } else {
                Log.d(TAG, "======ReDownloadTask=======run over===no net connect or app is ReDownloading====");
            }
        }
    }

    private void moveAndInstallApp(final String fileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = FileUtils.moveFile(mTempDownloadDir, mRealDonloadDir, fileName);
                if (result) {
                    String apkPath = mRealDonloadDir.getPath() + File.separator + fileName;
                    Log.e(TAG, "==moveAndInstallApp==temp apk path:" + mTempDownloadDir.getPath() + "   real apk path: " + mRealDonloadDir.getPath() + "  file name: " + fileName);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse("file://" + apkPath), "application/vnd.android.package-archive");
                    startActivity(intent);
                }
            }
        }).start();
    }
}
