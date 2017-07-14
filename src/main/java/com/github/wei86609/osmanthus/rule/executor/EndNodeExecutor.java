package com.github.wei86609.osmanthus.rule.executor;

import org.apache.log4j.Logger;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.monitor.RuleInfo;
import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.rule.Rule.TYPE;

public class EndNodeExecutor extends CommonExecutor{

    private final static Logger logger = Logger.getLogger(EndNodeExecutor.class);

    @Override
    public String run(Event event, Rule rule,RuleInfo ruleInfo) throws Exception {
        logger.debug("The start node["+rule.getId()+"] of the event{"+event+"} executed.");
        return null;
    }

    @Override
    public TYPE getType() {
        return TYPE.END;
    }

}
