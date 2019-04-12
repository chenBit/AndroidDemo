package com.chenbit.demo.bean;

import java.io.Serializable;

/**
 * Created by NSKY on 2018/7/3.
 */

public class AppInfo implements Serializable {
    public String url;
    public String name;
    public int progress;
    public int status;

    public AppInfo(String name, String url) {
        this.name = name;
        this.url = url;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof AppInfo) {
            AppInfo appInfo = (AppInfo) obj;
            return url.equals(appInfo.url) && name.equals(appInfo.name) && progress == appInfo.progress && status == appInfo.status;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", progress=" + progress +
                ", status=" + status +
                '}';
    }
}
