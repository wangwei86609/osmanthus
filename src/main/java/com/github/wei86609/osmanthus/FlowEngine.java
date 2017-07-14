package com.github.wei86609.osmanthus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.rule.Rule.TYPE;
import com.github.wei86609.osmanthus.rule.executor.CommonExecutor;

public class FlowEngine{

    private final static Logger logger = Logger.getLogger(FlowEngine.class);

    private Map<TYPE,CommonExecutor> ruleExecutorMap;

    public void addRuleExecutor(List<CommonExecutor> ruleExecutors){
        if(ruleExecutorMap==null){
            ruleExecutorMap=new HashMap<TYPE,CommonExecutor>();
        }
        for(CommonExecutor ruleExecutor:ruleExecutors){
            ruleExecutorMap.put(ruleExecutor.getType(), ruleExecutor);
        }
    }

    public void addRuleExecutor(CommonExecutor ruleExecutor){
        if(ruleExecutorMap==null){
            ruleExecutorMap=new HashMap<TYPE,CommonExecutor>();
        }
        ruleExecutorMap.put(ruleExecutor.getType(), ruleExecutor);
    }

    public Boolean execute(Event event,String ruleId) throws Exception {
        logger.debug("Osmanthus start to execute the event["+event+"]");
        Rule firstRule=ConfigurationBuilder.getBuilder().getFirstRuleByFlow(event.getFlowId());
        if(StringUtils.isBlank(ruleId)){
            ruleId=firstRule.getId();
            logger.debug("Node is blank, will get the first rule ["+ruleId+"] of flow to execute.");
        }
        runFlowRule(event,ruleId);
        logger.debug("Osmanthus execute the event {"+event+"} end");
        return true;
    }

    private void runFlowRule(Event event,String ruleId)throws Exception{
        if(StringUtils.isBlank(ruleId)){
            return;
        }
        Rule rule = getRuleOfFlowById(event, ruleId);
        if(rule==null){
            return;
        }
        String nextRuleId= ruleExecutorMap.get(rule.getType()).execute(event, rule);
        logger.debug("Current rule["+ruleId+"]'s next rule id is["+nextRuleId+"]");
        runFlowRule(event,nextRuleId);
    }

    private Rule getRuleOfFlowById(Event event, String nodeId) throws Exception {
        return ConfigurationBuilder.getBuilder().getRuleByFlow(event.getFlowId(), nodeId);
    }

    public void runRule(Event event,String ruleId)throws Exception{
        if(StringUtils.isBlank(ruleId)){
            return;
        }
        Rule rule=getRuleOfFlowById(event, ruleId);
        if(rule==null){
            return;
        }
        ruleExecutorMap.get(rule.getType()).execute(event, rule);
    }

    public void runExternalRule(Event event,String ruleId)throws Exception{
        if(StringUtils.isBlank(ruleId)){
            return;
        }
        Rule rule=ConfigurationBuilder.getBuilder().getExternalRuleById(ruleId);
        if(rule==null){
            return;
        }
        ruleExecutorMap.get(rule.getType()).execute(event, rule);
    }

}
