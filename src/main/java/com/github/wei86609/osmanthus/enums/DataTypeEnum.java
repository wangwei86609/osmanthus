/*
 * Copyright (c) 2017 PaiPai Credit Data Services (Shanghai) Co., Ltd All Rights Reserved.
 */

package com.github.wei86609.osmanthus.enums;

/**
 * @author wangwei86609
 */
public enum DataTypeEnum {

    INTEGER("INTEGER"), DOUBLE("DOUBLE"), DATE("DATE"), BOOLEAN("BOOLEAN"), STRING(
            "STRING");

    private String type;

    /**
     * @param name
     */
    private DataTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

}
