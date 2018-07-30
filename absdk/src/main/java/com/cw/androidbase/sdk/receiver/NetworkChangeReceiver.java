package com.cw.androidbase.sdk.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.cw.androidbase.sdk.app.BaseApp;
import com.cw.androidbase.sdk.utils.NetworkUtils;

import java.util.List;

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = NetworkUtils.getNetworkState(context);
            notifyAll(netWorkState);
        }
    }

    /**
     * 网络状态变化的接口
     */
    public interface NetEventListener {
        void onNetChange(int netWorkState);
    }

    /**
     * 通知所有注册网络监听的Listener
     *
     * @param netWorkState
     */
    private void notifyAll(int netWorkState) {
        List<NetEventListener> listeners = BaseApp.getNetListenerList();
        if (listeners == null || listeners.size() == 0) {
            return;
        }
        for (NetEventListener listener : listeners) {
            listener.onNetChange(netWorkState);
        }
    }
}