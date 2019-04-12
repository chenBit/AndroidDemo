package com.cw.androidbase.sdk.utils;

import android.util.Log;

import com.cw.androidbase.sdk.app.BaseApp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AssetUtil {
    private static final String TAG = AssetUtil.class.getSimpleName();

    private static final String IP_CONFIG_NAME_NEW = "config.properties";
    private static final String KEY_ACTIVE_IP = "active_ip";
    private static final String KEY_PRE_VERIFY_IP = "pre_verify_ip";

    public static String getConfigIp(int index) {
        try {
            InputStream is = BaseApp.getAppContext().getAssets().open(IP_CONFIG_NAME_NEW);
            Properties properties = new Properties();
            properties.load(is);
            is.close();
            String key = index == 0 ? KEY_ACTIVE_IP : KEY_PRE_VERIFY_IP;
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getAssetConfig(String fileName){
        InputStream ins =  null;
        String config = "";
        try{
            ins = BaseApp.getAppContext().getAssets().open(fileName);
            byte b[] = new byte[ins.available()];
            ins.read(b);
            config = new String(b);
            Log.d(TAG,"====getAssetConfig===" + config);
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG,"====getAssetConfig===exception:" + e);
        }finally {
            try{
                if(ins != null) ins.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            return config;
        }
    }
}
