package com.github.wei86609.osmanthus.rule;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("end")
public class End extends Rule {

    @Override
    public String getToRuleId() {
        return null;
    }

    @Override
    public String getId() {
        return "end";
    }

    @Override
    public RuleType getType() {
        return RuleType.END;
    }
}
