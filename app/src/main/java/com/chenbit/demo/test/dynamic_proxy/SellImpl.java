package com.chenbit.demo.test.dynamic_proxy;

import android.util.Log;

/**
 * Created by NSKY on 2018/10/31.
 */

public class SellImpl implements ISell {
    @Override
    public void sell(String name) {
        Log.e("DDD","今天卖了个：" + name);
    }
}
