package com.github.wei86609.osmanthus.rule.executor;

import org.apache.log4j.Logger;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.rule.Rule.TYPE;

public class EndNodeExecutor implements RuleExecutor{

    private final static Logger logger = Logger.getLogger(EndNodeExecutor.class);

    public String execute(Event event, Rule rule) throws Exception {
        logger.debug("The start node["+rule.getId()+"] of the event{"+event+"} executed.");
        return null;
    }

    public TYPE getType() {
        return TYPE.END;
    }

    public void stop() {

    }

}
