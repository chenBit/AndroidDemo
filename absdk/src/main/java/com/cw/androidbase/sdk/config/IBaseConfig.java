package com.cw.androidbase.sdk.config;

import com.cw.androidbase.sdk.db.ITable;

import java.util.List;

public interface IBaseConfig {

    int initUIThemeColor();


    int initDBVersion();


    String initDBName();

    /**
     * 获取数据库库中所有表的类
     *
     * @return
     */
    List<Class<? extends ITable>> getAllTables();

    String initSPName();
}
