package com.cw.androidbase.sdk.log;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;

import com.cw.androidbase.sdk.utils.DateUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: 全局异常捕获处理类
 * Author:Created by Rambo on 2017/1/22.
 * Email:1988chenwei@gmail.com
 */

public class CrashHandler implements UncaughtExceptionHandler {
    public static final String TAG = CrashHandler.class.getSimpleName();
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private static CrashHandler mInstance;
    private Context mContext;
    private Map<String, String> mCollectInfoMap;

    private CrashHandler() {
        mCollectInfoMap = new HashMap<>();
    }

    public static CrashHandler getInstance() {
        if (mInstance == null) {
            synchronized (CrashHandler.class) {
                if (mInstance == null) {
                    mInstance = new CrashHandler();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        mContext = context;
        File crashDir = Logger.LOG_CRASH_DIR;
        if (!crashDir.exists()) {
            boolean result = crashDir.mkdirs();
            Logger.d(TAG, "=====RESULT====" + result);
        }
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Logger.d(TAG, "==uncaughtException==");
        if (ex != null) {
            ex.printStackTrace();
        }
        mCollectInfoMap.put("Thread error name", thread.getName());
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Logger.e(TAG, "error : " + e);
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }

    /**
     * 系统未捕获得异常，自己处理
     *
     * @param ex
     * @return true:
     */
    private boolean handleException(Throwable ex) {
        Logger.d(TAG, "==handleException==");
        if (ex == null) {
            return false;
        }
        writeInfo2SDCard(ex);
        return true;
    }

    /**
     * 将crash 日志写到sdcard 文件中
     *
     * @param ex
     */
    public void writeInfo2SDCard(final Throwable ex) {
        Logger.d(TAG, "==writeInfo2SDCard==");
        new Thread(new Runnable() {

            @Override
            public void run() {
                collectDeviceInfo(mContext);
                StringBuffer sb = new StringBuffer();
                for (Map.Entry<String, String> entry : mCollectInfoMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    sb.append(key + "=" + value + "\n");
                }
                Logger.d(TAG, "==writeInfo2SDCard==device info===" + sb.toString());
                saveCrashInfo2File(ex, sb);
            }
        }).start();
    }

    /**
     * 收集设备信息 以及APP版本号、版本名称
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        Logger.d(TAG, "==collectDeviceInfo==");
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                mCollectInfoMap.put("versionName", versionName);
                mCollectInfoMap.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Logger.e(TAG, "an error occur when collect package info:" + e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mCollectInfoMap.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Logger.e(TAG, "an error occur when collect crash info:" + e);
            }
        }
    }

    /**
     * 把异常信息写到sdcard里。
     *
     * @param ex
     * @return
     */
    private void saveCrashInfo2File(Throwable ex, StringBuffer sb) {
        Logger.d(TAG, "==saveCrashInfo2File==");
        String time = DateUtil.getDateTimeStr(DateUtil.DATE_TIME_FORMAT_LONG);
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause = cause.getCause();
        }
        String result = info.toString();
        printWriter.close();
        sb.append(result);
        String fileName = "crash-" + time;
        File file = new File(Logger.LOG_CRASH_DIR, fileName);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            FileOutputStream fileOutputStream = null;
            try {
                file.createNewFile();
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(sb.toString().getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}