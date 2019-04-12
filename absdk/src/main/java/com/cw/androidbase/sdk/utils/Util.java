package com.cw.androidbase.sdk.utils;

import android.content.Intent;
import android.content.IntentFilter;

public class Util {
    /** @return Intent for the default home activity */
    public static Intent getHomeIntent() {
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        return intent;
    }

    /** @return IntentFilter for the default home activity */
    public static IntentFilter getHomeIntentFilter() {
        final IntentFilter filter = new IntentFilter(Intent.ACTION_MAIN);
        filter.addCategory(Intent.CATEGORY_HOME);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        return filter;
    }
}
