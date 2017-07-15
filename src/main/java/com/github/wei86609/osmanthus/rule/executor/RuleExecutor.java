package com.github.wei86609.osmanthus.rule.executor;

import com.github.wei86609.osmanthus.Executor;
import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.rule.Rule.TYPE;


public interface RuleExecutor extends Executor<Rule,String>{

    public String execute(Event event,Rule rule)throws Exception;

    public TYPE getType();

    public void stop();
}
