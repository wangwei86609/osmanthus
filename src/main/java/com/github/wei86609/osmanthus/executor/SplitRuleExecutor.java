package com.github.wei86609.osmanthus.executor;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.rule.Split;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SplitRuleExecutor extends RuleExecutor {

    private static final Logger logger = LoggerFactory
            .getLogger(SplitRuleExecutor.class);

    @Override
    public String run(Event event, Rule rule) {
        Split split = (Split) rule;
        for (Rule r : split.getRules()) {
            if (executeConditions(event, r)) {
                logger.info(
                        "{},  the result of condition is true, will link to rule[{}].",
                        event.getId(), r.getToRuleId());
                return r.getToRuleId();
            }
        }
        logger.warn(
                "{}, the split [{}] Seems constraint's conditions are all mismatch.",
                event.getId(), split.getId());
        return null;
    }

    @Override
    public Rule.RuleType getType() {
        return Rule.RuleType.SPLIT;
    }

}
