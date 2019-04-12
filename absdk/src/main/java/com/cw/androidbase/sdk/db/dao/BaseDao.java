package com.cw.androidbase.sdk.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.cw.androidbase.sdk.app.BaseApp;
import com.cw.androidbase.sdk.config.IBaseConfig;
import com.cw.androidbase.sdk.db.DBHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description: Dao基类
 * Author:Created by Rambo on 2017/1/20.
 * Email:1988chenwei@gmail.com
 */
public abstract class BaseDao<T> {
    private static final String TAG = BaseDao.class.getSimpleName();
    private DBHelper mDBHelper;

    public BaseDao() {
        IBaseConfig config = BaseApp.getInstance();
        mDBHelper = DBHelper.getInstance(BaseApp.getAppContext(), config);
    }

    protected SQLiteDatabase getReadableDB() {
        if (mDBHelper != null) {
            return mDBHelper.getReadableDatabase();
        }
        return null;
    }

    protected SQLiteDatabase getWritableDB() {
        if (mDBHelper != null) {
            return mDBHelper.getWritableDatabase();
        }
        return null;
    }

    /**
     * 从游标中取一个对象
     *
     * @param cursor cursor
     * @return t
     */
    protected abstract T findByCursor(Cursor cursor);

    /**
     * 根据游标获取列表
     *
     * @param cursor cursor
     * @return List
     */
    protected List<T> findListByCursor(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        return findListByCursor(cursor, cursor.getCount());
    }

    /**
     * 根据游标获取指定个数的列表
     */
    private List<T> findListByCursor(Cursor cursor, int size) {
        List<T> list = new ArrayList<>();
        cursor.moveToFirst();
        int n = 0;
        while (!cursor.isAfterLast() && (n < size)) {
            T t = findByCursor(cursor);
            list.add(t);
            cursor.moveToNext();
            n++;
        }
        return list;
    }
}
