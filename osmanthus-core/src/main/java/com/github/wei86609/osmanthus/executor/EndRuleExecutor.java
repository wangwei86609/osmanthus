package com.github.wei86609.osmanthus.executor;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.rule.Rule;


public class EndRuleExecutor extends RuleExecutor {

    public String run(Event event, Rule rule) {
        return null;
    }

    @Override
    public Rule.RuleType getType() {
        return Rule.RuleType.END;
    }

}
