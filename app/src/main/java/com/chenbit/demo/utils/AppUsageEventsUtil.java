package com.chenbit.demo.utils;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.chenbit.demo.app.DemoApp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AppUsageEventsUtil {
    private static final String TAG = "AppUsageEventsUtil";
    private static final String KEY_LAUNCHER = "launcher";

    public static List<AppUsageEvent> getUsageStats() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
            return null;
        }
        Context context = DemoApp.getAppContext();
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        if (usm == null) {
            return null;
        }
        long beginTime = getStartTimeOfDay();
        long endTime = System.currentTimeMillis();
        Log.d(TAG, "======getUsageStatsList====begin time: " + beginTime + "===end time: " + endTime);
        Map<String, UsageStats> usageStatsMap = usm.queryAndAggregateUsageStats(beginTime, endTime);
        Map<String, AppUsageEvent> usageEvents = getUsageEvents(usm, beginTime, endTime);
        return filter(usageStatsMap, usageEvents);
    }

    private static HashMap<String, AppUsageEvent> getUsageEvents(UsageStatsManager usm, long beginTime, long endTime) {
        UsageEvents events = usm.queryEvents(beginTime, endTime);
        List<AppUsageEvent> eventList = new ArrayList();
        while (events.hasNextEvent()) {
            UsageEvents.Event e = new UsageEvents.Event();
            events.getNextEvent(e);
            if (e != null && e.getEventType() == 1) {
                AppUsageEvent event = new AppUsageEvent();
                event.pkgName = e.getPackageName();
                eventList.add(event);
            }
        }
        if (eventList.size() == 0) {
            return null;
        }
        HashMap<String, AppUsageEvent> usageEventMap = new HashMap();
        for (int i = 0; i < eventList.size(); i++) {
            AppUsageEvent curEvent = eventList.get(i);
            String packageName = curEvent.pkgName;
            //判断所有事件中当前如果为launcher时去判断上一个事件,上一个事件不是launcher就手动添加到mPkgs，并且打开次数+1
            if (packageName.contains(KEY_LAUNCHER) && i > 0) {
                AppUsageEvent preEvent = eventList.get(i - 1);
                String pkgName = preEvent.pkgName;
                if (!pkgName.contains(KEY_LAUNCHER)) {
                    preEvent.usageCount = usageEventMap.get(pkgName) == null ? 1 : usageEventMap.get(pkgName).usageCount + 1;
                    usageEventMap.put(pkgName, preEvent);
                    Log.d(TAG, "Event:-" + preEvent.pkgName + "  count:" + preEvent.usageCount);
                }
            }

            //如果为最后一个事件，不是launcher就做上面相同的操作
            if (i == eventList.size() - 1) {
                if (!packageName.contains(KEY_LAUNCHER)) {
                    curEvent.usageCount = usageEventMap.get(packageName) == null ? 1 : usageEventMap.get(packageName).usageCount + 1;
                    usageEventMap.put(packageName, curEvent);
                    Log.d(TAG, "last  Event:-" + curEvent.pkgName + "  count: " + curEvent.usageCount);
                }
            }
        }
        return usageEventMap;
    }

    private static List<AppUsageEvent> filter(Map<String, UsageStats> usageStats, Map<String, AppUsageEvent> usageEvents) {
        if (usageEvents == null || usageStats == null) {
            return null;
        }
        Set<String> pkgSet = usageEvents.keySet();
        List<AppUsageEvent> eventList = new ArrayList<>();
        for (String pkgName : pkgSet) {
            UsageStats us = usageStats.get(pkgName);
            AppUsageEvent event = new AppUsageEvent();
            event.pkgName = us.getPackageName();
            event.usageDuration = us.getTotalTimeInForeground();
            event.usageCount = usageEvents.get(pkgName).usageCount;
            eventList.add(event);
        }
        return eventList;
    }

    private static long getStartTimeOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }
}
