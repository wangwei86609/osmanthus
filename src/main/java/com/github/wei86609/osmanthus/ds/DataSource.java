/*
 * Copyright (c) 2017 PaiPai Credit Data Services (Shanghai) Co., Ltd All Rights Reserved.
 */

package com.github.wei86609.osmanthus.ds;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author wangwei19
 */
public class DataSource {

    @XStreamAsAttribute
    private String name;

    @XStreamAsAttribute
    private String id;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
