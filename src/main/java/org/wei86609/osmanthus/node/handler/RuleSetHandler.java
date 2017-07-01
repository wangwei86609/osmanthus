package org.wei86609.osmanthus.node.handler;

import org.wei86609.osmanthus.Accepter;
import org.wei86609.osmanthus.node.ruleset.RuleSet;

public interface RuleSetHandler extends Accepter<RuleSet>{

    public void handle(RuleSet ruleSet);

    public boolean accept(RuleSet ruleSet);
}
