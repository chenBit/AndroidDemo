package com.chenbit.demo.observer;

import android.content.Context;
import android.os.Handler;

/**
 * Created by NSKY on 2018/3/29.
 */

public class ImageMediaObserver extends BaseMediaObserver {
    public ImageMediaObserver(Context context, Handler handler) {
        super(context, handler);
    }

    @Override
    protected void setUri() {
    }
}