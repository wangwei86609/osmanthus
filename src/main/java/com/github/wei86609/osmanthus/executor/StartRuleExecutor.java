package com.github.wei86609.osmanthus.executor;


import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.rule.Rule;

public class StartRuleExecutor extends RuleExecutor {


    public String run(Event event, Rule rule) {
        return rule.getToRuleId();
    }

    @Override
    public Rule.RuleType getType() {
        return Rule.RuleType.START;
    }

}
