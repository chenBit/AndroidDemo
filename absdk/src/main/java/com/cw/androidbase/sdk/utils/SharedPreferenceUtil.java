package com.cw.androidbase.sdk.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.cw.androidbase.sdk.app.BaseApp;

/**
 * Description: SharedPreference工具类
 * Author:Created by Rambo on 2017/2/7.
 * Email:1988chenwei@gmail.com
 */

public class SharedPreferenceUtil {
    private static SharedPreferenceUtil mInstance;
    private static final String SP_NAME = "study8_sp";
    private final SharedPreferences mSharedPreference;
    private final SharedPreferences.Editor mEditor;

    private SharedPreferenceUtil() {
        mSharedPreference = BaseApp.getAppContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreference.edit();
    }

    public static SharedPreferenceUtil getInstance() {
        if (mInstance == null) {
            synchronized (SharedPreferenceUtil.class) {
                if (mInstance == null) {
                    mInstance = new SharedPreferenceUtil();
                }
            }
        }
        return mInstance;
    }

    public void save(String key, String value) {
        mEditor.putString(key, value);
        mEditor.apply();
    }

    public String getString(String key, String defaultValue) {
        return mSharedPreference.getString(key, defaultValue);
    }

    public void save(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public int getInt(String key, int defaultValue) {
        return mSharedPreference.getInt(key, defaultValue);
    }
}
