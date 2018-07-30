package com.cw.androidbase.sdk.ui.config;

/**
 * Created by chen on 2017/3/16.
 */
public class UIConfiguration {
    private static int mUIThemeColor;

    public static void setThemeColor(int color) {
        mUIThemeColor = color;
    }

    public static int getThemeColor() {
        return mUIThemeColor;
    }
}
