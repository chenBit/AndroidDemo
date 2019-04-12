package com.chenbit.demo.download;

import android.os.Process;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.chenbit.demo.R;
import com.chenbit.demo.bean.AppInfo;
import com.cw.androidbase.sdk.ui.activity.BaseActivity;

import java.util.ArrayList;

public class DownloadActivity extends BaseActivity implements DownloadManager.OnUIDownloadListener {
    private static final String TAG = DownloadActivity.class.getSimpleName();
    private ListView lvDownload;
    private ArrayList<AppInfo> mAppInfos;
    private DownloadAdapter mAdapter;

    @Override
    protected int initContentViewId() {
        return R.layout.activity_download;
    }

    @Override
    protected void initView() {
        lvDownload = findViewById(R.id.lvDownload);
    }

    @Override
    protected void initData() {
        mAppInfos = new ArrayList<>();
        mAppInfos.add(new AppInfo("天天飞车.apk", "http://imtt.dd.qq.com/16891/9E38D98E708C4F25BA9E97C31405CB17.apk?fsname=com.tencent.game.SSGame_3.6.1.673_461673.apk&csr=db5e"));
        mAppInfos.add(new AppInfo("Tim.apk", "http://42.236.95.12/sqdd.myapp.com/myapp/qqteam/tim/down/tim.apk?mkey=5b3768e66f0ae19a&f=3502&c=0&p=.apk"));
        mAppInfos.add(new AppInfo("银行.apk", "http://imtt.dd.qq.com/16891/FF5E135727F9AE55F6E198835441B2FA.apk?fsname=com.chinamworld.main_4.1.0_244.apk&csr=db5e"));
        mAppInfos.add(new AppInfo("网易公开课.apk", "http://file.ws.126.net/opencourse/netease_open_androidphone.apk"));
        mAppInfos.add(new AppInfo("百度网盘", "http://issuecdn.baidupcs.com/issue/netdisk/yunguanjia/BaiduNetdisk_6.1.0.exe"));
        mAppInfos.add(new AppInfo("Notepad++", "https://notepad-plus-plus.org/repository/7.x/7.5.7/npp.7.5.7.Installer.x64.exe"));
        mAppInfos.add(new AppInfo("TeamViewer", "https://dl.tvcdn.de/download/TeamViewer_Setup.exe"));
        mAppInfos.add(new AppInfo("WeChat.apk", "http://dldir1.qq.com/weixin/android/weixin666android1300.apk"));
        mAppInfos.add(new AppInfo("美图秀秀.apk", "http://gdown.baidu.com/data/wisegame/c75bfe509e9c787d/meituxiuxiu_7400.apk"));
        mAppInfos.add(new AppInfo("FFF.APK", "https://emm.abchina.com/data/app/mdm%2F1%2Fb%2F9e95274e-bf50-4de8-9f19-b17a6e81736f.apk"));
        //
        mAdapter = new DownloadAdapter(this, mAppInfos);
        lvDownload.setAdapter(mAdapter);

        DownloadManager.getInstance().setUIDownloadListener(this);
        Log.e(TAG, "===DownloadActivity===" + Process.myPid());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DownloadManager.getInstance().setUIDownloadListener(null);
        Log.e(TAG, "===onDestroy===");
    }

    @Override
    public void onProgress(String tag, int progress) {
        AppInfo appInfo = mAdapter.findDataByTag(tag);
        if (appInfo != null) {
            appInfo.progress = progress;
            appInfo.status = progress == 100 ? DownloadService.STATUS_COMPLETED : DownloadService.STATUS_DOWNLOADING;
            int position = mAdapter.findPositionByTag(tag);
            if (isNeedUpdate(position)) {
                updateProgress(position, progress);
            }
        }
    }

    @Override
    public void onStatus(String tag, int status) {
        AppInfo appInfo = mAdapter.findDataByTag(tag);
        if (appInfo != null) {
            appInfo.status = status;
            Log.e(TAG, "===onStatus===" + status + "==url==" + tag);
            int position = mAdapter.findPositionByTag(tag);
            if (isNeedUpdate(position)) {
                updateStatus(position, status);
            }
        }
    }

    private boolean isNeedUpdate(int positionInAdapter) {
        return positionInAdapter >= lvDownload.getFirstVisiblePosition() && positionInAdapter <= lvDownload.getLastVisiblePosition();
    }

    private void updateProgress(int positionInAdapter, int progress) {
//        Log.e(TAG, "===updateProgress===" + progress + "==positionInAdapter==" + positionInAdapter + "==firstVisiblePosition==" + lvDownload.getFirstVisiblePosition());
        int position = positionInAdapter - lvDownload.getFirstVisiblePosition();
        ProgressBar pb = lvDownload.getChildAt(position).findViewById(R.id.pb);
        pb.setProgress(progress);
    }

    private void updateStatus(int positionInAdapter, int status) {
//        Log.e(TAG, "===updateStatus===" + status + "==positionInAdapter==" + positionInAdapter + "==firstVisiblePosition==" + lvDownload.getFirstVisiblePosition());
        int position = positionInAdapter - lvDownload.getFirstVisiblePosition();
        Button btnStatus = lvDownload.getChildAt(position).findViewById(R.id.btnStatus);
        mAdapter.setContentByStatus(status, btnStatus);
    }
}
