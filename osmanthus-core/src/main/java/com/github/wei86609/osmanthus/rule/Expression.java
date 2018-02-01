/*
 * Copyright (c) 2017 PaiPai Credit Data Services (Shanghai) Co., Ltd All Rights Reserved.
 */

package com.github.wei86609.osmanthus.rule;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author wangwei19
 */
@XStreamAlias("expression")
public class Expression {

    private Vocabulary leftVoc;

    private String op;

    private Vocabulary rightVoc;

    public Vocabulary getLeftVoc() {
        return this.leftVoc;
    }

    public void setLeftVoc(Vocabulary leftVoc) {
        this.leftVoc = leftVoc;
    }

    public String getOp() {
        return this.op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Vocabulary getRightVoc() {
        return this.rightVoc;
    }

    public void setRightVoc(Vocabulary rightVoc) {
        this.rightVoc = rightVoc;
    }

}
