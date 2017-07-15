package com.github.wei86609.osmanthus.rule.executor;

import org.apache.log4j.Logger;
import org.mvel2.MVEL;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.rule.Rule.TYPE;

public class MergeNodeExecutor implements RuleExecutor{

    private final static Logger logger = Logger.getLogger(MergeNodeExecutor.class);

    public String execute(Event event, Rule rule) throws Exception {
        logger.debug("Merge rule ["+rule.getId()+"] executed");
        return null;
    }

    protected boolean executeCondition(String condition, Event context) {
        return (Boolean)MVEL.eval(condition, context.getVariables());
    }

    public TYPE getType() {
        return TYPE.MERGE;
    }

    public void stop() {

    }

}
