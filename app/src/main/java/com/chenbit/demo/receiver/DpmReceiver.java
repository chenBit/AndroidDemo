package com.chenbit.demo.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;

import com.chenbit.demo.app.DemoApp;
import com.chenbit.demo.download.DownloadActivity;

/**
 * Created by NSKY on 2018/4/26.
 */
public class DpmReceiver extends DeviceAdminReceiver {
    private static final String TAG = DpmReceiver.class.getSimpleName();

    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        Log.d(TAG,"====onEnabled====");
        intent = new Intent(context, DownloadActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        Log.d(TAG,"====onDisabled====");
    }

    public static ComponentName getComponentName(){
        return new ComponentName(DemoApp.getAppContext(), DpmReceiver.class);
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent, UserHandle user) {
        super.onPasswordFailed(context, intent, user);
    }
}
