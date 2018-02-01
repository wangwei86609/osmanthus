package com.github.wei86609.osmanthus.rule;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("parallel")
public class Parallel extends RuleSet {

    @Override
    public RuleType getType() {
        return RuleType.PARALLEL;
    }

}
