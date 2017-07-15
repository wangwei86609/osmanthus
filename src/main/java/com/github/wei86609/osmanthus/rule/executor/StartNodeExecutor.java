package com.github.wei86609.osmanthus.rule.executor;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.rule.Rule.TYPE;

public class StartNodeExecutor implements RuleExecutor{

    public String execute(Event event, Rule rule) throws Exception {
        return rule.getToRuleId();
    }

    public TYPE getType() {
        return TYPE.START;
    }

    public void stop() {

    }

}
