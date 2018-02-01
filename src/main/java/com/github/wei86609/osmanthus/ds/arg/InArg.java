/*
 * Copyright (c) 2017 PaiPai Credit Data Services (Shanghai) Co., Ltd All Rights Reserved.
 */

package com.github.wei86609.osmanthus.ds.arg;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * @author wangwei19
 */
@XStreamAlias("inArgs")
public class InArg {

    @XStreamImplicit
    private List<Arg> args;

    public List<Arg> getArgs() {
        return this.args;
    }

    public void setArgs(List<Arg> args) {
        this.args = args;
    }

    public void addArg(Arg arg) {
        if (this.args == null) {
            this.args = new ArrayList<Arg>();
        }
        this.args.add(arg);
    }
}
