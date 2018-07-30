package com.chenbit.demo.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * 数据库操作的管理类，对数据库表的增删改查通过该类的对象实现
 */
public class CWSQLiteManager {
    private static final String TAG = CWSQLiteManager.class.getSimpleName();
    private static CWSQLiteManager mInstance;
    private static Context mContext;
    private CWSQLiteHelper mDBHelper;
    private SQLiteDatabase mDB;

    private CWSQLiteManager() {
        mDBHelper = new CWSQLiteHelper(mContext);
        mDB = mDBHelper.getWritableDatabase();
    }

    public static CWSQLiteManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (CWSQLiteManager.class) {
                if (mInstance == null) {
                    mInstance = new CWSQLiteManager();
                    mContext = context.getApplicationContext();
                }
            }
        }
        return mInstance;
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
