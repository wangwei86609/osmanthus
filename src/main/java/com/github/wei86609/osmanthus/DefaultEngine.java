package com.github.wei86609.osmanthus;

import com.github.wei86609.osmanthus.event.EngineIntercepter;
import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.executor.RuleExecutor;
import com.github.wei86609.osmanthus.rule.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DefaultEngine {

    private final static Logger logger = LoggerFactory
            .getLogger(DefaultEngine.class);

    private Map<Rule.RuleType, RuleExecutor> ruleExecutorMap;

    private final List<EngineIntercepter> engineIntercepters = new ArrayList<EngineIntercepter>();

    public void addEngineIntercepter(EngineIntercepter engineIntercepter) {
        this.engineIntercepters.add(engineIntercepter);
    }

    public void addRuleExecutor(RuleExecutor ruleExecutor) {
        if (this.ruleExecutorMap == null) {
            this.ruleExecutorMap = new HashMap<Rule.RuleType, RuleExecutor>();
        }
        this.ruleExecutorMap.put(ruleExecutor.getType(), ruleExecutor);
    }

    protected String executeRule(Event event, Rule rule) {
        String nextRuleId = null;
        try {
            executeEventInterceptor(InterceptorType.RULE_BEFORE, event, rule,
                    null);
            // perform rule
            nextRuleId = this.ruleExecutorMap.get(rule.getType()).execute(
                    event, rule);
            executeEventInterceptor(InterceptorType.RULE_POST, event, rule,
                    null);
        } catch (Exception e) {
            logger.error(
                    "Event[{}], execute current rule[{}] occurs exception",
                    event.getId(), rule.getId());
            executeEventInterceptor(InterceptorType.RULE_EXCEPTION, event,
                    rule, e);
        }
        return nextRuleId;
    }

    protected void executeEventInterceptor(InterceptorType interceptorType,
            Event event, Rule rule, Exception e) {
        for (int i = 0; i < this.engineIntercepters.size(); i++) {
            EngineIntercepter eventListener = this.engineIntercepters.get(i);
            if (eventListener != null) {
                if (InterceptorType.EVENT_INIT.equals(interceptorType)) {
                    eventListener.init(event);
                } else if (InterceptorType.EVENT_COMPLETE
                        .equals(interceptorType)) {
                    eventListener.complete(event);
                } else if (InterceptorType.EVENT_EXCEPTION
                        .equals(interceptorType)) {
                    eventListener.exception(event, e);
                } else if (InterceptorType.RULE_BEFORE.equals(interceptorType)) {
                    eventListener.beforeRule(event, rule);
                } else if (InterceptorType.RULE_POST.equals(interceptorType)) {
                    eventListener.afterRule(event, rule);
                } else if (InterceptorType.RULE_EXCEPTION
                        .equals(interceptorType)) {
                    eventListener.hasErrorRule(event, rule, e);
                } else if (InterceptorType.RULESET_INIT.equals(interceptorType)) {
                    eventListener.initRuleSet(event, rule);
                }
            }
        }
    }
}
