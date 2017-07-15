package com.github.wei86609.osmanthus.rule;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("merge")
public class Merge extends Rule{

    private int lineCnt;

    public synchronized int getLineCnt() {
        return lineCnt;
    }

    public void setLineCnt(int lineCnt) {
        this.lineCnt = lineCnt;
    }

    @Override
    public TYPE getType() {
        return TYPE.MERGE;
    }

}
