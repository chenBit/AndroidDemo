package com.cw.androidbase.sdk.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cw.androidbase.sdk.app.BaseApp;
import com.cw.androidbase.sdk.config.IBaseConfig;
import com.cw.androidbase.sdk.log.Logger;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Description: 数据库SQLiteOpenHelper
 * Author:Created by Rambo on 2017/1/20.
 * Email:1988chenwei@gmail.com
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = DBHelper.class.getSimpleName();
    private static DBHelper mInstance;
    private static IBaseConfig mDBConfig;
    private Context mContext;

    private DBHelper(Context context, IBaseConfig config) {
        super(context, config.initDBName(), null, config.initDBVersion());
        Logger.d(TAG, "====DBHelper===");
        mDBConfig = config;
        mContext = context;
    }

    public static DBHelper getInstance(Context context, IBaseConfig config) {
        if (mInstance == null) {
            synchronized (DBHelper.class) {
                if (mInstance == null) {
                    mInstance = new DBHelper(context, config);
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        List<Class<? extends ITable>> classes = mDBConfig.getAllTables();
        Logger.d(TAG, "====onCreate====table size===" + classes.size());
        for (Class<? extends ITable> clazz : classes) {
            try {
                Constructor<? extends ITable> con = clazz.getConstructor();
                ITable table = con.newInstance();
                table.onCreate(db);
                Logger.d(TAG, "create table  " + clazz.getName());
            } catch (Exception e) {
                Logger.d(TAG, "create table  " + clazz.getName() + e.toString());
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
