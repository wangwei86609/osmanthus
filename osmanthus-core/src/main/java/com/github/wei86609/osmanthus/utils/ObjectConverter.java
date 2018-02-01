/*
 * Copyright (c) 2017 PaiPai Credit Data Services (Shanghai) Co., Ltd All Rights Reserved.
 */

package com.github.wei86609.osmanthus.utils;

/**
 * @author wangwei19
 */
public abstract class ObjectConverter {

    public static boolean convertObj(Boolean obj) {
        if (obj == null) {
            return false;
        }
        return obj;
    }

    public static int convertObj(Integer obj) {
        if (obj == null) {
            return 0;
        }
        return obj;
    }

    public static double convertObj(Double obj) {
        if (obj == null) {
            return 0;
        }
        return obj;
    }


}
