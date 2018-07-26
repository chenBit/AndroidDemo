package com.example.nsky.studydemo.utils;

import android.content.Context;
import android.util.Log;

import java.io.InputStream;

public class AssetUtil {
    private static final String TAG = AssetUtil.class.getSimpleName();

    public static String getAssetConfig(Context context, String fileName){
        InputStream ins =  null;
        String config = "";
        try{
            ins = context.getAssets().open(fileName);
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
