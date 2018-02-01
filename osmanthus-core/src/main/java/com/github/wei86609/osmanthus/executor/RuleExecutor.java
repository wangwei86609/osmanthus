package com.github.wei86609.osmanthus.executor;

import com.github.wei86609.osmanthus.el.ElBuilder;
import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.exception.RuleException;
import com.github.wei86609.osmanthus.rule.Action;
import com.github.wei86609.osmanthus.rule.Condition;
import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.utils.ObjectConverter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RuleExecutor {

    private static final Logger logger = LoggerFactory
            .getLogger(RuleExecutor.class);

    public String execute(Event event, Rule rule) throws Exception {
        String toRuleId = null;
        try {
            toRuleId = run(event, rule);
        } catch (Exception e) {
            logger.error("{},execute current rule {} occurs exception!",
                    event.getId(), rule.getId(), e);
            throw new RuleException(e.getMessage());
        }
        return toRuleId;
    }

    protected boolean executeConditions(Event event, Rule rule) {
        if (CollectionUtils.isEmpty(rule.getConditions())) {
            return true;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < rule.getConditions().size(); i++) {
            Condition cond = rule.getConditions().get(i);
            boolean result = executeCondition(event, cond);
            if (i > 0) {
                builder.append(StringUtils.SPACE)
                        .append(rule.getMatchedPattern())
                        .append(StringUtils.SPACE);
            }
            builder.append(result);
        }
        return (Boolean) ElBuilder.build().executeStrExp(builder.toString(),
                event);
    }

    private boolean executeCondition(Event event, Condition cond) {
        if (CollectionUtils.isEmpty(cond.getConditionGroup())) {
            return ElBuilder.build().executeCondition(cond.getExp(), event);
        }
        String op = cond.getExp().getOp();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < cond.getConditionGroup().size(); i++) {
            Condition subCond = cond.getConditionGroup().get(i);
            boolean result = ElBuilder.build().executeCondition(
                    subCond.getExp(), event);
            if (i > 0) {
                builder.append(StringUtils.SPACE).append(op)
                        .append(StringUtils.SPACE);
            }
            builder.append(result);
        }
        return (Boolean) ElBuilder.build().executeStrExp(builder.toString(),
                event);
    }

    protected void executeActions(Event event, Rule rule) {
        if (CollectionUtils.isEmpty(rule.getActions())) {
            return;
        }
        for (Action action : rule.getActions()) {
            ElBuilder.build().executeAction(action, event);
        }
    }

    protected String run(Event event, Rule rule) {
        boolean isHit = executeConditions(event, rule);
        rule.setIsHit(isHit);
        logger.info("{}, hit status[{}] of rule[{}]", event.getId(), isHit,
                rule.getId());
        if (isHit) {
            if (!ObjectConverter.convertObj(rule.getIsPilotRun())) {
                logger.info(
                        "{}, because of rule[{}] is not pilot run, will begin to execute its actions.",
                        event.getId(), rule.getId());
                executeActions(event, rule);
            }
        }
        return rule.getToRuleId();
    }

    public Rule.RuleType getType() {
        return Rule.RuleType.RULE;
    }

    public void stop() {

    }
}
