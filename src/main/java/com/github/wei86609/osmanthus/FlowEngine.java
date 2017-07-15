package com.github.wei86609.osmanthus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.intercepter.Intercepter;
import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.rule.Rule.TYPE;
import com.github.wei86609.osmanthus.rule.executor.RuleExecutor;

public class FlowEngine{

    private final static Logger logger = Logger.getLogger(FlowEngine.class);

    private Map<TYPE,RuleExecutor> ruleExecutorMap;

    private final List<Intercepter> ruleIntercepters;

    public FlowEngine(){
        ruleIntercepters=new ArrayList<Intercepter>();
    }

    public void addRuleExecutor(List<RuleExecutor> ruleExecutors){
        if(ruleExecutorMap==null){
            ruleExecutorMap=new HashMap<TYPE,RuleExecutor>();
        }
        for(RuleExecutor ruleExecutor:ruleExecutors){
            ruleExecutorMap.put(ruleExecutor.getType(), ruleExecutor);
        }
    }

    public void addRuleInterceptor(Intercepter ruleIntercepter){
        ruleIntercepters.add(ruleIntercepter);
    }

    public void addRuleExecutor(RuleExecutor ruleExecutor){
        if(ruleExecutorMap==null){
            ruleExecutorMap=new HashMap<TYPE,RuleExecutor>();
        }
        ruleExecutorMap.put(ruleExecutor.getType(), ruleExecutor);
    }

    public Boolean execute(Event event) throws Exception {
        logger.debug("Osmanthus start to execute the event["+event+"]");
        Rule firstRule=ConfigurationBuilder.getBuilder().getFirstRuleByFlow(event.getFlowId());
        if(StringUtils.isBlank(event.getCurrentRuleId())){
            event.setCurrentRuleId(firstRule.getId());
            logger.debug("Node is blank, will get the first rule ["+firstRule.getId()+"] of flow to execute.");
        }
        runFlowRule(event,event.getCurrentRuleId());
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
        try {
            event.setCurrentRuleId(ruleId);
            beforeIntercepter(event, rule);
            String nextRuleId= ruleExecutorMap.get(rule.getType()).execute(event, rule);
            logger.debug("Current rule["+ruleId+"]'s next rule id is["+nextRuleId+"]");
            afterIntercepter(event, rule);
            runFlowRule(event,nextRuleId);
        } catch (Exception e) {
            exceptionIntercepter(event, rule, e);
            throw e;
        }
    }

    private void exceptionIntercepter(Event event, Rule rule, Exception e) {
        for(Intercepter intercepter :ruleIntercepters){
            if(intercepter!=null){
                intercepter.exception(e,event, rule);
            }
        }
    }

    private void afterIntercepter(Event event, Rule rule) {
        for(Intercepter intercepter :ruleIntercepters){
            if(intercepter!=null){
                intercepter.after(event, rule);
            }
        }
    }

    private void beforeIntercepter(Event event, Rule rule) {
        for(Intercepter intercepter :ruleIntercepters){
            if(intercepter!=null){
                intercepter.before(event, rule);
            }
        }
    }

    private Rule getRuleOfFlowById(Event event, String nodeId) throws Exception {
        return ConfigurationBuilder.getBuilder().getRuleByFlow(event.getFlowId(), nodeId);
    }

    public void runSingleRule(Event event,String ruleId)throws Exception{
        if(StringUtils.isBlank(ruleId)){
            return;
        }
        Rule rule=getRuleOfFlowById(event, ruleId);
        if(rule==null){
            return;
        }
        try {
            event.setCurrentRuleId(ruleId);
            beforeIntercepter(event, rule);
            String nextRuleId= ruleExecutorMap.get(rule.getType()).execute(event, rule);
            logger.debug("Current rule["+ruleId+"]'s next rule id is["+nextRuleId+"]");
            afterIntercepter(event, rule);
        } catch (Exception e) {
            exceptionIntercepter(event, rule, e);
            throw e;
        }
    }

    public void runExternalSingleRule(Event event,String ruleId)throws Exception{
        if(StringUtils.isBlank(ruleId)){
            return;
        }
        Rule rule=ConfigurationBuilder.getBuilder().getExternalRuleById(ruleId);
        if(rule==null){
            return;
        }
        try {
            event.setCurrentRuleId(ruleId);
            beforeIntercepter(event, rule);
            String nextRuleId= ruleExecutorMap.get(rule.getType()).execute(event, rule);
            logger.debug("Current rule["+ruleId+"]'s next rule id is["+nextRuleId+"]");
            afterIntercepter(event, rule);
        } catch (Exception e) {
            exceptionIntercepter(event, rule, e);
            throw e;
        }
    }

}
