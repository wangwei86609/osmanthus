package com.github.wei86609.osmanthus.rule;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("end")
public class End extends Rule{

    @Override
    public String getToNodeId() {
        return null;
    }

    @Override
    public TYPE getType() {
        return TYPE.END;
    }
}
