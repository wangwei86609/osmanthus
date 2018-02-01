/*
 * Copyright (c) 2017 PaiPai Credit Data Services (Shanghai) Co., Ltd All Rights Reserved.
 */

package com.github.wei86609.osmanthus.rule.ds.arg;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author wangwei19
 */
@XStreamAlias("arg")
public class Arg {

    @XStreamAsAttribute
    private String varId;

    @XStreamAsAttribute
    private String varName;

    @XStreamAsAttribute
    private String refVarName;

    public String getVarName() {
        return this.varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public String getVarId() {
        return this.varId;
    }

    public void setVarId(String varId) {
        this.varId = varId;
    }

    public String getRefVarName() {
        return refVarName;
    }

    public void setRefVarName(String refVarName) {
        this.refVarName = refVarName;
    }
}
