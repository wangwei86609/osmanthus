package com.github.wei86609.osmanthus;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.rule.RuleSet;
import com.github.wei86609.osmanthus.utils.ObjectConverter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleSetEngine extends DefaultEngine {

    private static final Logger logger = LoggerFactory
            .getLogger(RuleSetEngine.class);

    public boolean execute(Event event, RuleSet ruleSet) {
        try {
            if (ruleSet == null) {
                logger.error("{}, don't find any ruleset[{}]", event.getId());
                return false;
            }
            // will trigger once the event initial
            executeEventInterceptor(InterceptorType.EVENT_INIT, event, null,
                    null);
            // will run rules of rule set
            executeRuleSet(event, ruleSet);
            // will trigger once the event complete
            executeEventInterceptor(InterceptorType.EVENT_COMPLETE, event,
                    null, null);
            return true;
        } catch (Exception e) {
            logger.error("{}, execute ruleset occurs exception", event.getId(),
                    e);
            executeEventInterceptor(InterceptorType.EVENT_EXCEPTION, event,
                    null, e);
        }
        return false;
    }

    /**
     * 执行规则，包括：简单的规则和规则集
     *
     * @param event
     * @param rule
     * @return
     */
    private String executeHybridRules(Event event, Rule rule) {
        if (isRuleSetType(rule.getType())) {
            RuleSet ruleSet = (RuleSet) rule;
            executeRuleSet(event, ruleSet);
            return ruleSet.getToRuleId();
        } else {
            return executeRule(event, rule);
        }
    }

    /**
     * @param event
     * @param ruleSet
     */
    private void executeRuleSet(Event event, RuleSet ruleSet) {
        initRuleSet(event, ruleSet);
        executeRuleOfSet(event, ruleSet);
    }

    /**
     * 执行规则规则集
     *
     * @param event
     */
    private void executeRuleOfSet(Event event, RuleSet ruleSet) {
        if (ruleSet.getRulesMap() == null) {
            return;
        }
        Rule rule = ruleSet.getRulesMap().get(event.getCurrentRuleId());
        if (rule == null) {
            return;
        }
        int repeatTimes = ObjectConverter.convertObj(rule.getMultipleTimes());
        String nextRuleId = null;
        int times = 0;
        do {
            nextRuleId = executeHybridRules(event, rule);
            repeatTimes--;
            times++;
            logger.info("{}, the rule[{}] of ruleset[{}], executed times=[{}]",
                    event.getId(), rule.getId(), ruleSet.getId(), times);
        } while (repeatTimes > 0);
        if (ObjectConverter.convertObj(ruleSet.getOrder())
                && !ObjectConverter.convertObj(rule.getIsHit())) {
            logger.info(
                    "{}, the rule[{}] of ruleset[{}] is not hit, will return.",
                    event.getId(), rule.getId(), ruleSet.getId());
            return;
        }
        if (StringUtils.isNotEmpty(nextRuleId)) {
            logger.info(
                    "{}, next rule of current rule[{}] is [{}], executed times=[{}]",
                    event.getId(), rule.getId(), nextRuleId, times);
            event.setCurrentRuleId(nextRuleId);
        } else {
            logger.warn(
                    "{}, next rule of current rule[{}] is null, executed times=[{}]",
                    event.getId(), rule.getId(), times);
            return;
        }
        if (ObjectConverter.convertObj(rule.getIsHit())
                && RuleSet.FIRST_MATCH.equalsIgnoreCase(ruleSet.getModel())) {
            logger.info(
                    "{}, the ruleset[{}] is first match, so remain rules will not be executed.",
                    event.getId(), ruleSet.getId());
            return;
        }
        executeRuleOfSet(event, ruleSet);
    }

    /**
     * @param event
     * @param ruleSet
     */
    private void initRuleSet(Event event, RuleSet ruleSet) {
        // initialize tactics
        logger.info("{}, initialize rules of ruleset[{}]", event.getId(),
                ruleSet.getId());
        ruleSet.init();
        executeEventInterceptor(InterceptorType.RULESET_INIT, event, ruleSet,
                null);
        Rule firstRule = ruleSet.getStartRule();
        if (firstRule == null) {
            logger.error(
                    "{}, don't find one start rule of the ruleset[{}] in the event[{}]",
                    event.getId(), event.getStrategyId());
            return;
        }
        logger.info("{}, the first rule is [{}] of ruleset [{}].",
                event.getId(), firstRule.getId(), ruleSet.getId());
        // set current rule id into event
        event.setCurrentRuleId(firstRule.getId());
    }

    private boolean isRuleSetType(Rule.RuleType ruleType) {
        return Rule.RuleType.CARD.equals(ruleType)
                || Rule.RuleType.SET.equals(ruleType);
    }

}
