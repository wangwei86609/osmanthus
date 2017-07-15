package com.github.wei86609.osmanthus.rule.executor;

import org.apache.log4j.Logger;
import org.mvel2.MVEL;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.rule.Rule.TYPE;

public class MvelRuleExecutor implements RuleExecutor {

    private final static Logger logger = Logger.getLogger(MvelRuleExecutor.class);

    public TYPE getType() {
        return TYPE.RULE;
    }

    public void stop() {

    }

    public String execute(Event event,Rule rule) throws Exception{
        boolean success=executeCondition(rule,event);
        if(success){
            logger.debug("The node["+rule.getId()+"] of the event {"+event.getEventId()+"} condition=["+rule.getCondition()+"] is true and action=["+rule.getAction()+"]");
            executeAction(rule,event);
            return rule.getToRuleId();
        }
        return null;
    }

    protected boolean executeCondition(Rule rule, Event event) {
        return (Boolean)MVEL.eval(rule.getCondition(), event.getVariables());
    }

    protected Object executeAction(Rule rule, Event event) {
        return MVEL.eval(rule.getAction(), event.getVariables());
    }

}
