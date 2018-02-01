/*
 * Copyright (c) 2017 PaiPai Credit Data Services (Shanghai) Co., Ltd All Rights Reserved.
 */

package com.github.wei86609.osmanthus.rule;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author wangwei19
 */
@XStreamAlias("action")
public class Action {

    private Vocabulary returnVoc;

    private String type;

    private Expression exp;

    public Vocabulary getReturnVoc() {
        return this.returnVoc;
    }

    public void setReturnVoc(Vocabulary returnVoc) {
        this.returnVoc = returnVoc;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Expression getExp() {
        return this.exp;
    }

    public void setExp(Expression exp) {
        this.exp = exp;
    }

}
