package com.github.wei86609.osmanthus.rule.executor;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.mvel2.MVEL;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.rule.Constraint;
import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.rule.Rule.TYPE;
import com.github.wei86609.osmanthus.rule.Split;

public class SplitRuleExecutor implements RuleExecutor{

    private final static Logger logger = Logger.getLogger(SplitRuleExecutor.class);

    public String execute(Event event, Rule rule) throws Exception {
        Split split=(Split)rule;
        logger.debug("Split["+split.getId()+"] of the event {"+event+"} has ["+split.getConstraints().size()+"] Constraints");
        for(Constraint c:split.getConstraints()){
            if(executeCondition(c.getCondition(),event)){
                logger.debug("Split["+split.getId()+"] of the event {"+event+"} Constraint's condition ["+c.getCondition()+"] result is true, will link to Node["+c.getToNodeId()+"].");
                return c.getToNodeId();
            }
        }
        if(StringUtils.isEmpty(split.getToNodeId())){
            logger.error("The node["+split.getId()+"] of the event {"+event+"} Seems constraint's conditions are all mismatch.");
            throw new Exception("The node["+split.getId()+"] of the event {"+event+"} Seems constraint's conditions are all mismatch.");
        }
        return null;
    }

    protected boolean executeCondition(String condition, Event context) {
        return (Boolean)MVEL.eval(condition, context.getVariables());
    }

    public TYPE getType() {
        return TYPE.SPLIT;
    }

    public void stop() {

    }

}
