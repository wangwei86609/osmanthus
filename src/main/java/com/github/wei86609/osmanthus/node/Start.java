package com.github.wei86609.osmanthus.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("start")
public class Start extends Node{

    @Override
    public String getFromNodeId() {
        return null;
    }

    @Override
    public TYPE getType() {
        return TYPE.EMPTY;
    }
}
