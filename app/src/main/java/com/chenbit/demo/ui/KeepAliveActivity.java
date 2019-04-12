package com.chenbit.demo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by LV on 2016/12/26.
 * empty activity for keep alive
 */

public class KeepAliveActivity extends Activity {
    private static final String TAG = KeepAliveActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            Window window = getWindow();
            window.setGravity(Gravity.LEFT | Gravity.TOP);
            WindowManager.LayoutParams params = window.getAttributes();
            params.x = 0;
            params.y = 0;
            params.height = 1;
            params.width = 1;
            // 屏蔽点击事件
//            params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
            window.setAttributes(params);
        }catch (Exception ex){
            Log.e(TAG,"exception while start keep alive activity:" + ex);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setVisible(true);
    }
}
