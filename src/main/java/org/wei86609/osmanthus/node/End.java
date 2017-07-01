package org.wei86609.osmanthus.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("end")
public class End extends Node{

    @Override
    public String getToNodeId() {
        return null;
    }

    @Override
    public TYPE getType() {
        return TYPE.EMPTY;
    }
}
