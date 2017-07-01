package org.wei86609.osmanthus.node.handler;

import java.util.List;

import org.wei86609.osmanthus.Accepter;
import org.wei86609.osmanthus.node.Rule;
import org.wei86609.osmanthus.node.ruleset.GeneralRuleSet;

public interface RuleSetHandler extends Accepter<GeneralRuleSet>{

    public List<Rule> handle(List<Rule> rules);

    public boolean accept(GeneralRuleSet ruleSet);
}
