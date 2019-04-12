package com.chenbit.demo.test.binder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class TestBinderService extends Service {
    public TestBinderService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ITestBinder.Stub() {
            @Override
            public void fuckBinder() throws RemoteException {
                Log.e("fuck","===fuck binder===");
            }
        };
    }
}
