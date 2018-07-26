package com.example.nsky.studydemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.nsky.studydemo.app.DemoApp;
import com.example.nsky.studydemo.receiver.NetworkChangeReceiver;

public abstract class BaseActivity extends AppCompatActivity implements NetworkChangeReceiver.NetEventListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        DemoApp.getNetListenerList().add(this);
    }

    protected abstract void initView();

    protected abstract void initData();

    protected void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }
}
