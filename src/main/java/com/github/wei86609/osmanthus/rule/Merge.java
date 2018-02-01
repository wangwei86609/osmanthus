package com.github.wei86609.osmanthus.rule;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("merge")
public class Merge extends Rule {

    private int lineCnt;

    public synchronized int getLineCnt() {
        return this.lineCnt;
    }

    public synchronized void setLineCnt(int lineCnt) {
        this.lineCnt = lineCnt;
    }

    @Override
    public RuleType getType() {
        return RuleType.MERGE;
    }

}
