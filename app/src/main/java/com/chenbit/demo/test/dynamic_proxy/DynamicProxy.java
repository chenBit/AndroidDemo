package com.chenbit.demo.test.dynamic_proxy;


import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by NSKY on 2018/10/31.
 */

public class DynamicProxy {
    private ISell seller;

    public DynamicProxy() {
        seller = new SellImpl();
    }

    public ISell getProxy() {
        return (ISell) Proxy.newProxyInstance(ISell.class.getClassLoader(), new Class[]{ISell.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Log.d("fffuuuccc", "我是动态代理--start==" );
                ISell ss = (ISell) method.invoke(seller, args);
                Log.d("fffuuuccc", "我是动态代理--end");
                return ss;
            }
        });
    }
}
