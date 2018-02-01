/*
 * Copyright (c) 2017 PaiPai Credit Data Services (Shanghai) Co., Ltd All Rights Reserved.
 */

package com.github.wei86609.osmanthus.rule;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author wangwei19
 */
@XStreamAlias("condition")
public class Condition {

    private String id;

    private String type;

    private Expression exp;

    private List<Condition> conditionGroup;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Condition> getConditionGroup() {
        return this.conditionGroup;
    }

    public void setConditionGroup(List<Condition> conditionGroup) {
        this.conditionGroup = conditionGroup;
    }

}
