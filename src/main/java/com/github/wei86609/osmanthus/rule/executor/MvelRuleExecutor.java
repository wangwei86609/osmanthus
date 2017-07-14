package com.github.wei86609.osmanthus.rule.executor;

import org.apache.log4j.Logger;
import org.mvel2.MVEL;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.monitor.RuleInfo;
import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.rule.Rule.TYPE;

public class MvelRuleExecutor extends CommonExecutor {

    private final static Logger logger = Logger.getLogger(MvelRuleExecutor.class);

    @Override
    public TYPE getType() {
        return TYPE.RULE;
    }

    @Override
    public String run(Event event,Rule rule,RuleInfo ruleInfo) throws Exception{
        ruleInfo.setCondition(rule.getCondition());
        ruleInfo.setAction(rule.getAction());
        boolean success=executeCondition(rule,event);
        if(success){
            ruleInfo.setResultOfConditoin(success);
            logger.debug("The node["+rule.getId()+"] of the event {"+event.getEventId()+"} condition=["+rule.getCondition()+"] is true and action=["+rule.getAction()+"]");
            Object obj=executeAction(rule,event);
            ruleInfo.setResultOfAction(obj);
            return rule.getToNodeId();
        }
        return null;
    }

    protected boolean executeCondition(Rule rule, Event event) {
        return (Boolean)MVEL.eval(rule.getCondition(), event.getVars());
    }

    protected Object executeAction(Rule rule, Event event) {
        return MVEL.eval(rule.getAction(), event.getVars());
    }

}
