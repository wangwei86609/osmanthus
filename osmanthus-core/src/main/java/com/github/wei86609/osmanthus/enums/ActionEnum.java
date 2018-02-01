/*
 * Copyright (c) 2017 PaiPai Credit Data Services (Shanghai) Co., Ltd All Rights Reserved.
 */

package com.github.wei86609.osmanthus.enums;

/**
 * @author wangwei86609
 */
public enum ActionEnum {

    TEXT("text"), COMBINE("combine");

    private String type;

    /**
     * @param name
     */
    private ActionEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

}
