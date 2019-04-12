package com.chenbit.demo.utils;

/**
 * Created by zj on 2018/4/12.
 */

public class  AppUsageEvent {
    public String pkgName;
    public String version;
    public String appName;
    public String vendor;
    public int usageCount;
    public long usageDuration;
    public long mobileData;
    public long wifiData;

    @Override
    public String toString() {
        return "AppUsageEvent{" +
                "pkgName='" + pkgName + '\'' +
                ", version='" + version + '\'' +
                ", appName='" + appName + '\'' +
                ", vendor='" + vendor + '\'' +
                ", usageCount=" + usageCount +
                ", usageDuration=" + usageDuration +
                ", mobileData=" + mobileData +
                ", wifiData=" + wifiData +
                '}';
    }
}
