package com.github.wei86609.osmanthus.rule;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("split")
public class Split extends RuleSet {

    @Override
    public RuleType getType() {
        return RuleType.SPLIT;
    }
}
