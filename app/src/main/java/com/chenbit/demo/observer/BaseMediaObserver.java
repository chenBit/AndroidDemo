package com.chenbit.demo.observer;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;

import com.cw.androidbase.sdk.utils.SharedPreferenceUtil;

import java.util.Date;

/**
 * Created by NSKY on 2018/3/29.
 */

public abstract class BaseMediaObserver extends ContentObserver {
    private static final String TAG = BaseMediaObserver.class.getSimpleName();
    private Context mContext;
    protected Uri mUri;
    private Handler mHandler;
    private static final String FILTER_IMAGE = "IMG";
    private static final String FILTER_VIDEO = "VID";
    private static final String FILTER_VIVO_VIDEO = "video";
    private static final String KEY_MEDIA_VIDEO_COUNT = "key_media_video_count";
    private static final String KEY_MEDIA_IMAGE_COUNT = "key_media_image_count";
    public static final String KEY_MEDIA_URI = "key_media_uri";
    public static final String KEY_MEDIA_FILE_PATH = "key_media_file_path";
    private SharedPreferenceUtil mSPUtil;
    private String mNewPicFilePath;
    private String mSPKey;

    public BaseMediaObserver(Context context, Handler handler) {
        super(handler);
        mHandler = handler;
        mContext = context;
        mSPUtil = SharedPreferenceUtil.getInstance();
        setUri();
        initMediaCount();
    }

    private void initMediaCount() {
        if (mUri == null || TextUtils.isEmpty(mUri.getPath())) {
            return;
        }
        mSPKey = mUri.getPath().contains("video") ? KEY_MEDIA_VIDEO_COUNT : KEY_MEDIA_IMAGE_COUNT;
        int count = getCameraPicCount();
        Log.d(TAG, "initMediaCount--------uri-------" + mUri + "----count----" + count);
        mSPUtil.save(mSPKey, count);
    }

    protected abstract void setUri();

    /**
     * 获取手机相机拍摄的照片或视频个数
     *
     * @return 个数
     */
    private int getCameraPicCount() {
        int cameraPicCount = 0;
        Cursor cursor = mContext.getContentResolver().query(mUri, null, null, null, "_data desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String filePath = cursor.getString(cursor.getColumnIndex("_data"));
                if (isCameraPath(filePath)) {
                    if (cameraPicCount == 0) {
                        mNewPicFilePath = filePath;
                    }
                    cameraPicCount++;
                }
            }
            Log.d(TAG, "getCameraPicCount--------uri-------" + mUri + "----pic count----" + cameraPicCount);
            cursor.close();
        }
        return cameraPicCount;
    }

    private boolean isCameraPath(String filePath) {
        CharSequence curDate = DateFormat.format("yyyMMdd", new Date());
        Log.d(TAG, "=========isCameraPath--filePath==========" + filePath + "==cur date==" + curDate);
        return !TextUtils.isEmpty(filePath) && filePath.contains(curDate) && (filePath.contains(FILTER_IMAGE) || filePath.contains(FILTER_VIDEO) || filePath.contains(FILTER_VIVO_VIDEO));
    }

    /**
     * 系统图库内容发生变化
     */
    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        if (mUri == null || TextUtils.isEmpty(mUri.getPath())) {
            return;
        }
        Log.d(TAG, "system media lib is changed!---uri---" + mUri);
        int curCount = getCameraPicCount();
        int lastCount = mSPUtil.getInt(mSPKey, 0);
        Log.d(TAG, "cur camera media count is:" + curCount + "   last count: " + lastCount);
        mSPUtil.save(mSPKey, curCount);
        if (curCount <= lastCount) { //用户删数据，ignore
            return;
        }
        Log.d(TAG, "onChange--filePath---" + mNewPicFilePath);
        notifyUploadMediaInfo();
    }

    private void notifyUploadMediaInfo() {
        if (mHandler != null) {
            Message message = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putParcelable(KEY_MEDIA_URI, mUri);
            bundle.putString(KEY_MEDIA_FILE_PATH, mNewPicFilePath);
            message.setData(bundle);
            mHandler.sendMessage(message);
        }
    }
}