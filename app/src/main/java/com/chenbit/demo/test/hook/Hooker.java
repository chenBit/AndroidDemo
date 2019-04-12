package com.chenbit.demo.test.hook;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by NSKY on 2018/11/7.
 */
public class Hooker {
    public  int fuck;

    public static void hookInstrumention() throws Exception {
        Class<?> aClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThread = aClass.getDeclaredMethod("currentActivityThread");
        currentActivityThread.setAccessible(true);
        Object activityThread = currentActivityThread.invoke(aClass);
        Field mInstrumentation = aClass.getDeclaredField("mInstrumentation");
        mInstrumentation.setAccessible(true);
        mInstrumentation.set(activityThread, new TestInstrumentation());
    }
}
