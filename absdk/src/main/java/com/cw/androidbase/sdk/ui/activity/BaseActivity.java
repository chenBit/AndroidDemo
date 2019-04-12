package com.cw.androidbase.sdk.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.cw.androidbase.sdk.app.BaseApp;
import com.cw.androidbase.sdk.utils.StatusBarUtil;
import com.cw.androidbase.sdk.utils.ViewUtil;

public abstract class BaseActivity extends Activity {
    private Handler mHandler;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        mContext = getApplicationContext();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int viewId = initContentViewId();
        setContentView(viewId);
        initView();
        checkAndSetUITheme();
        initData();
    }

    /**
     * 设置状态栏颜色
     * 注意：只有5.0以上才生效
     */
    protected void checkAndSetUITheme() {
        int sdkVersion = getApplicationInfo().targetSdkVersion;
        if (sdkVersion >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarUtil.setColor(this, getResources().getColor(BaseApp.getInstance().initUIThemeColor()));
        }
    }

    protected abstract int initContentViewId();

    protected abstract void initView();

    protected abstract void initData();

    protected void showToast(int contentId) {
        ViewUtil.showToast(contentId);
    }

    protected void showToast(String content) {
        ViewUtil.showToast(content);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}