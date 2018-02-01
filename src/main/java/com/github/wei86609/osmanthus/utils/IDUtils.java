package com.github.wei86609.osmanthus.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Calendar;
import java.util.UUID;

public abstract class IDUtils {

    public static String getEventId() {
        String prefix = DateFormatUtils.format(Calendar.getInstance(), "yyyyMMdd");
        return new StringBuilder().append(prefix).append(getUUID()).toString();
    }

    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        return (s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24));
    }

    public static String getTacticId(String prefix) {
        return new StringBuilder().append(prefix).append(getUUID()).toString();
    }

    public static String getId(String prefix) {
        return new StringBuilder().append(prefix).append(getUUID()).toString();
    }


}
