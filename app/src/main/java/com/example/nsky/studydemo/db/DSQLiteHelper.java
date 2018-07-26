package com.example.nsky.studydemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * 数据库、表的创建、更新
 */
public class DSQLiteHelper extends SQLiteOpenHelper {
    private static final String TAG = DSQLiteHelper.class.getSimpleName();
    private Context mContext;
    //数据库相关
    private static final String DB_NAME = "xx.db";//数据库的名字
    private static final int DB_VERSION = 1; //数据库的版本号，每次数据库的表结构发生变化，修改这个值

    //表相关
    public static final String TABLE_NAME = "t_area";//表的名字
    private static final String COLUMN_ID = "id"; //id
    private static final String COLUMN_PROVINCE_NUMBER = "province_number"; //省市编号
    private static final String COLUMN_PROVINCE_NAME = "province_name"; //省市名称
    private static final String COLUMN_CITY_NUMBER = "city_number"; //城市编号
    private static final String COLUMN_CITY_NAME = "city_name"; //城市名称

    private static final String COLUMN_LONGITUDE = "longitude"; //经度
    private static final String COLUMN_LATITUDE = "latitude"; //纬度
    private static final String COLUMN_CREATE_BY = "create_by"; //创建者
    private static final String COLUMN_CREATE_DATE = "create_date"; //创建时间

    private static final String COLUMN_UPDATE_BY = "update_by"; //更新者
    private static final String COLUMN_UPDATE_DATE = "update_date"; //更新时间
    private static final String COLUMN_REMARKS = "remarks"; //备注信息
    private static final String COLUMN_IS_DELETED = "isdeleted"; //删除标记
    public DSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE IF NOT EXISTS " + TABLE_NAME);
        sb.append("(");
        sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT");
        sb.append("," + COLUMN_ID + " INTEGER");
        sb.append("," + COLUMN_PROVINCE_NUMBER + " TEXT");
        sb.append("," + COLUMN_PROVINCE_NAME + " TEXT");
        sb.append("," + COLUMN_CITY_NUMBER + " TEXT");
        sb.append("," +COLUMN_CITY_NAME + " TEXT");
        sb.append("," + COLUMN_LONGITUDE + " TEXT");

        sb.append("," + COLUMN_LATITUDE + " TEXT");
        sb.append("," + COLUMN_CREATE_BY + " TEXT");
        sb.append("," + COLUMN_CREATE_DATE + " TEXT");
        sb.append("," +COLUMN_UPDATE_BY + " TEXT");
        sb.append("," + COLUMN_UPDATE_DATE + " TEXT");

        sb.append("," + COLUMN_REMARKS + " TEXT");
        sb.append("," + COLUMN_IS_DELETED + " INTEGER");
        sb.append(")");
        Log.d(TAG, "exec sql:" + sb.toString());
        db.execSQL(sb.toString());
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
