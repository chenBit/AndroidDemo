package com.cw.androidbase.sdk.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.cw.androidbase.sdk.ui.config.UIConfiguration;
import com.cw.androidbase.sdk.utils.StatusBarUtil;
import com.cw.androidbase.sdk.utils.ViewUtil;

public abstract class BaseActivity extends AppCompatActivity {
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
        initStatusBar();
        initData();
    }

    /**
     * 设置状态栏颜色
     * 注意：只有5.0以上才生效
     */
    protected void initStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(UIConfiguration.getThemeColor()));
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