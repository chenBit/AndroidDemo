package com.cw.androidbase.sdk.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.cw.androidbase.sdk.app.BaseApp;

public class ViewUtil {

    private static Resources getResources() {
        return getContext().getResources();
    }

    private static Context getContext() {
        return BaseApp.getAppContext();
    }

    public static void setTextSize(TextView textView, int sizeId) {
        if (textView != null) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(sizeId));
        }
    }

    public static void showToast(int contentId) {
        Toast toast = Toast.makeText(getContext(), contentId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToast(String content) {
        Toast toast = Toast.makeText(getContext(), content, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    public static void setTextDrawable(TextView view, int drawableId) {
        Drawable drawable = getResources().getDrawable(drawableId);// 找到资源图片
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置图片宽高
        view.setCompoundDrawables(drawable, null, null, null);// 设置到控件中
    }
}
