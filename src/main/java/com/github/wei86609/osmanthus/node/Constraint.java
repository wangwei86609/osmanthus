package com.github.wei86609.osmanthus.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("constraint")
public class Constraint extends Node{

    private String condition;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

}
