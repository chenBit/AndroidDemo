package com.example.nsky.studydemo;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by NSKY on 2018/3/29.
 */

public class MediaContentObserver extends ContentObserver {
    private static final String TAG = MediaContentObserver.class.getSimpleName();
    private Context mContext;
    private int index;

    public MediaContentObserver(Context context, Handler handler) {
        super(handler);
        mContext = context;
    }

    /**
     * 主要在onChange中响应数据库变化，并进行相应处理
     */
    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        index++;
        Log.i(TAG, "database is changed!------------------------------------------" + selfChange);
//        if(index == 2) {
//            index = 0;
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, "_data desc");
            if (cursor != null) {
//                Log.i(TAG, "VIDEO====The number of data is:" + cursor.getCount());
                while (cursor.moveToNext()) {
                    String fileName = cursor.getString(cursor.getColumnIndex("_data"));
//                    Log.i(TAG, "VIDEO====fileName------------------------------------------" + fileName);
                }
                cursor.close();
            }
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            cursor = mContext.getContentResolver().query(uri, null, null, null, "_data desc");
            if (cursor != null) {
                Log.i(TAG, "IMAGES====The number of data is:" + cursor.getCount());
                while (cursor.moveToNext()) {
                    String fileName = cursor.getString(cursor.getColumnIndex("_data"));
                    Log.i(TAG, "IMAGES====fileName------------------------------------------" + fileName);
                }
                cursor.close();
            }
//        }
    }
}