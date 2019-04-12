package com.chenbit.demo.test.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.util.Log;

import com.chenbit.demo.app.DemoApp;
import com.chenbit.demo.ui.MainActivity;

/**
 * Created by NSKY on 2018/11/7.
 */

public class TestInstrumentation extends Instrumentation {

    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class newClass = MainActivity.class;
        intent = new Intent(DemoApp.getAppContext(), newClass);
        Log.d("test","===hook new Activity===before: " + className + "====after: " + newClass.getName());
        return super.newActivity(cl, newClass.getName(), intent);
    }
}
