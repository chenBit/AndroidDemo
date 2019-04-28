package com.chenbit.demo.test.sso;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.chenbit.demo.R;
import com.nationsky.emmsdk.service.aidl.IEmmSsoCallback;
import com.nationsky.emmsdk.service.aidl.ResultInfo;
import com.nationsky.ssosdk.api.EmmSsoSDK;

public class TestSsoActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sso_test);
        findViewById(R.id.btnRequestSSO).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmmSsoSDK.requestSso(new IEmmSsoCallback.Stub() {
                    @Override
                    public void onSsoResult(ResultInfo resultInfo) {
                        Log.d("TestSsoActivity", "====requestSso resultInfo====" + resultInfo);
                    }
                });
            }
        });


        findViewById(R.id.btnGetToken).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmmSsoSDK.getTokenInfo(new IEmmSsoCallback.Stub() {
                    @Override
                    public void onSsoResult(ResultInfo resultInfo) {
                        Log.d("TestSsoActivity", "====getTokenInfo resultInfo====" + resultInfo);
                    }
                });
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
