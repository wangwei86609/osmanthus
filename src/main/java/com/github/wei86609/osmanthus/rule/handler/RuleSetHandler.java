package com.github.wei86609.osmanthus.rule.handler;

import com.github.wei86609.osmanthus.Accepter;
import com.github.wei86609.osmanthus.rule.ruleset.RuleSet;

public interface RuleSetHandler extends Accepter<RuleSet>{

    public void handle(RuleSet ruleSet);

    public boolean accept(RuleSet ruleSet);
}
