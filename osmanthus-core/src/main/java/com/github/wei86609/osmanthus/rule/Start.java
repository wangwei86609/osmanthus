package com.github.wei86609.osmanthus.rule;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("start")
public class Start extends Rule {

    @Override
    public String getFromRuleId() {
        return null;
    }

    @Override
    public String getId() {
        return "start";
    }

    @Override
    public RuleType getType() {
        return RuleType.START;
    }
}
