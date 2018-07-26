package com.example.nsky.studydemo.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 数据库操作的管理类，对数据库表的增删改查通过该类的对象实现
 */
public class DSQLiteManager {
    private static final String TAG = DSQLiteManager.class.getSimpleName();
    private static DSQLiteManager mInstance;
    private static Context mContext;
    private DSQLiteHelper mDBHelper;
    private SQLiteDatabase mDB;

    private DSQLiteManager() {
        mDBHelper = new DSQLiteHelper(mContext);
        mDB = mDBHelper.getWritableDatabase();
    }

    public static DSQLiteManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DSQLiteManager.class) {
                if (mInstance == null) {
                    mInstance = new DSQLiteManager();
                    mContext = context.getApplicationContext();
                }
            }
        }
        return mInstance;
    }

    public void executeAssetsSQL(String dbfilepath) {
        if (mDB == null) {
            return;
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(mContext.getAssets().open(dbfilepath)));
            Log.d(TAG, "路径:" + dbfilepath);
            String line;
            String buffer = "";
            //开启事务
            mDB.beginTransaction();
            while ((line = in.readLine()) != null) {
                buffer += line;
                if (line.trim().endsWith(";")) {
                    mDB.execSQL(buffer.replace(";", ""));
                    buffer = "";
                }
            }
            //设置事务标志为成功，当结束事务时就会提交事务
            mDB.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("db-error", e.toString());
        } finally {
            //事务结束
            mDB.endTransaction();
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                Log.e("db-error", e.toString());
            }
        }
    }

    public void insert(){

    }

    public void delete(){

    }

    public void update(){

    }

    public List query(){
        return null;
    }

}
