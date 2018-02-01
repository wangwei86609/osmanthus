/*
 * Copyright (c) 2017 PaiPai Credit Data Services (Shanghai) Co., Ltd All Rights Reserved.
 */

package com.github.wei86609.osmanthus.rule.ds;

import com.ppc.gypsophila.engine.core.rule.ds.arg.InArg;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author wangwei19
 */
@XStreamAlias("interface")
public class Interface extends DataSource {

    private String queryVarName;

    private String url;

    private String method;

    private InArg inArg;

    public InArg getInArg() {
        return this.inArg;
    }

    public void setInArg(InArg inArg) {
        this.inArg = inArg;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getQueryVarName() {
        return this.queryVarName;
    }

    public void setQueryVarName(String queryVarName) {
        this.queryVarName = queryVarName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((this.method == null) ? 0 : this.method.hashCode())
                + ((this.queryVarName == null) ? 0 : this.queryVarName
                        .hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Interface other = (Interface) obj;
        if (this.method == null) {
            if (other.method != null) {
                return false;
            }
        } else if (!this.method.equals(other.method)) {
            return false;
        }
        if (this.queryVarName == null) {
            if (other.queryVarName != null) {
                return false;
            }
        } else if (!this.queryVarName.equals(other.queryVarName)) {
            return false;
        }
        return true;
    }
}
