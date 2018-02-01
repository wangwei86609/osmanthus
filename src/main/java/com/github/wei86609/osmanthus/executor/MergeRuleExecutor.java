package com.github.wei86609.osmanthus.executor;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.rule.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MergeRuleExecutor extends RuleExecutor {

    private static final Logger logger = LoggerFactory
            .getLogger(MergeRuleExecutor.class);

    public String run(Event event, Rule rule) {
        logger.info("Event[{}], merge rule [{}] executed", event.getId(),
                rule.getId());
        return null;
    }

    @Override
    public Rule.RuleType getType() {
        return Rule.RuleType.MERGE;
    }

}
