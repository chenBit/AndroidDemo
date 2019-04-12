package com.cw.androidbase.sdk.log;

import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.cw.androidbase.sdk.BuildConfig;
import com.cw.androidbase.sdk.utils.DateUtil;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Description: 程序的Log管理类
 * Author:Created by Rambo on 2017/1/20.
 * Email:1988chenwei@gmail.com
 */
public class Logger {
    private static final File mLogRootFile = Environment.getExternalStorageDirectory();
    private static final String TAG = Logger.class.getSimpleName();
    private static final String DEFAULT_PATH = "/Study8/";
    private static final String DEBUG = DEFAULT_PATH + "debug";
    private static final String ERROR = DEFAULT_PATH + "error";
    private static final String CRASH = DEFAULT_PATH + "crash";
    private static final File LOG_DEBUG_DIR = new File(mLogRootFile, DEBUG);
    private static final File LOG_ERROR_DIR = new File(mLogRootFile, ERROR);
    public static final File LOG_CRASH_DIR = new File(mLogRootFile, CRASH);
    private static final int LOG_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000; //7days

    public static void d(String tag, String content) {
        if (BuildConfig.SHOW_CONSOLE_LOG) {
            android.util.Log.d(tag, content);
        }
        writeLogWithSeg(LOG_DEBUG_DIR, tag, content);
    }


    public static void e(String tag, String content) {
        if (BuildConfig.SHOW_CONSOLE_LOG) {
            android.util.Log.e(tag, content);
        }
        writeLogWithSeg(LOG_ERROR_DIR, tag, content);
    }

    /**
     * 检查日志文件，删除过期日志
     */
    public static void checkAndDelExpiredLogFile() {
        Logger.d(TAG, "===checkAndDelExpiredLogFile===");
        File dirs[] = new File[]{LOG_DEBUG_DIR, LOG_ERROR_DIR, LOG_CRASH_DIR};
        final long current = System.currentTimeMillis();
        final java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        for (File dir : dirs) {
            if (dir != null && dir.isDirectory()) {
                File[] files = dir.listFiles(new FileFilter() {

                    @Override
                    public boolean accept(File pathname) {
                        String fileName = pathname.getName();
                        Logger.d(TAG, "check file:" + fileName);
                        if (TextUtils.isEmpty(fileName) || !fileName.endsWith(".log")) {
                            return false;
                        }
                        try {
                            Date date = df.parse(fileName);
                            if (current - date.getTime() > LOG_EXPIRE_TIME) {
                                Logger.d(TAG, "expire log file:" + fileName);
                                return true;
                            }
                        } catch (ParseException e) {
                            Logger.e(TAG, "check file exception:" + e);
                        }
                        return false;
                    }
                });
                if (files != null) {
                    Logger.d(TAG, "expire file size：" + files.length);
                    for (File file : files) {
                        boolean isDelete = file.delete();
                        Logger.d(TAG, "delete expire log file:" + file.getName() + " delete result:" + isDelete);
                    }
                }
            }
        }
    }

    /**
     * 记录日志，带分段
     */
    private static void writeLogWithSeg(File file, String tag, String content) {
        if (!BuildConfig.WRITE_LOG) {
            return;
        }
        if (!file.exists()) {
            file.mkdirs();
        }

        String time1 = DateUtil.getDateTimeStr(DateUtil.DATE_TIME_FORMAT_LONG);
        String time2 = DateUtil.getDateTimeStr(DateUtil.DATE_TIME_FORMAT_SHORT);
        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.DONUT) {
                File log = new File(file, time2 + ".log");
                if (!log.getParentFile().exists()) {
                    log.getParentFile().mkdirs();
                }
                FileWriter out = new FileWriter(log, true);
                out.write("[" + time1 + "] " + tag + " : " + content + "\n");
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
