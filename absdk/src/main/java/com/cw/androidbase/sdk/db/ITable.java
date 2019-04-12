package com.cw.androidbase.sdk.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Description: 数据库表的接口规范
 * Author:Created by Rambo on 2017/1/20.
 * Email:1988chenwei@gmail.com
 */
public interface ITable {
    void onCreate(SQLiteDatabase database);

    void onUpdate(SQLiteDatabase database, int oldVersion, int newVersion);
}