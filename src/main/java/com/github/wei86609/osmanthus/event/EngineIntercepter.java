package com.github.wei86609.osmanthus.event;


import com.github.wei86609.osmanthus.exception.RuleException;
import com.github.wei86609.osmanthus.rule.Rule;

public interface EngineIntercepter {

    public void init(Event event);

    public void complete(Event event);

    public void exception(Event event, Exception e);

    public void initRuleSet(Event event, Rule ruleSet) throws RuleException;

    public void beforeRule(Event event, Rule rule) throws RuleException;

    public void afterRule(Event event, Rule rule) throws RuleException;

    public void hasErrorRule(Event event, Rule rule, Exception e);
}
