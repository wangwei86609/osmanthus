package com.github.wei86609.osmanthus.rule.executor;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.github.wei86609.osmanthus.Executor;
import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.monitor.RuleInfo;
import com.github.wei86609.osmanthus.monitor.RuleMonitor;
import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.rule.Rule.TYPE;


public abstract class CommonExecutor implements Executor<Rule,String>{

    private final static Logger logger = Logger.getLogger(CommonExecutor.class);

    private RuleMonitor ruleMonitor;

    public RuleMonitor getRuleMonitor() {
        return ruleMonitor;
    }

    public void setRuleMonitor(RuleMonitor ruleMonitor) {
        this.ruleMonitor = ruleMonitor;
    }

    public String execute(Event event,Rule rule){
        String nextNodeId=StringUtils.EMPTY;
        long startTime=System.currentTimeMillis();
        RuleInfo ruleInfo=new RuleInfo();
        try {
            ruleInfo.setEventId(event.getEventId());
            ruleInfo.setEventModel(event.getModel().name());
            ruleInfo.setFlowId(event.getFlowId());
            ruleInfo.setFromNodeId(rule.getFromNodeId());
            ruleInfo.setRuleId(rule.getId());
            ruleInfo.setRuleType(rule.getType().name());
            //run node
            nextNodeId=run(event,rule,ruleInfo);
            //enrich parameters
            ruleInfo.setToNodeId(nextNodeId);
            ruleInfo.setCostTime(System.currentTimeMillis()-startTime);
        } catch (Exception e) {
            event.setError(true);
            ruleInfo.setError(true);
            ruleInfo.setErrorMsg(e.getMessage());
            logger.error("Node["+rule.getId()+"] occurs exception",e);
        }
        ruleMonitor.oversee(ruleInfo);
        return nextNodeId;
    }

    public abstract String run(Event event, Rule rule,RuleInfo ruleInfo) throws Exception;

    public abstract TYPE getType();

    public void stop(){

    }
}
