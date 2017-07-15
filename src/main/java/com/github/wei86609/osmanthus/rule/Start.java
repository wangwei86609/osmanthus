package com.github.wei86609.osmanthus.rule;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("start")
public class Start extends Rule{

    @Override
    public String getFromRuleId() {
        return null;
    }

    @Override
    public TYPE getType() {
        return TYPE.START;
    }
}
