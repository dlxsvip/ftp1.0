package com.yyl.client.utils;

/**
 * Created by yl on 2016/10/27.
 */
public class TimeStr {

    public static long getTime(String tmp) {
        long cacheTime = 0;
        String granularity = "0";
        if (tmp.contains("s")) {
            granularity = tmp.replace("s", "");
            cacheTime = Long.parseLong(granularity) * 1000;
        } else if (tmp.contains("m")) {
            granularity = tmp.replace("m", "");
            cacheTime = Long.parseLong(granularity) * 1000 * 60;
        } else if (tmp.contains("h")) {
            granularity = tmp.replace("h", "");
            cacheTime = Long.parseLong(granularity) * 1000 * 60 * 60;
        } else if (tmp.contains("d")) {
            granularity = tmp.replace("d", "");
            cacheTime = Long.parseLong(granularity) * 1000 * 60 * 60 * 24;
        } else {

        }

        return cacheTime;
    }
}
