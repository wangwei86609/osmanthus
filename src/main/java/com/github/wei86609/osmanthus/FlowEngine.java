package com.github.wei86609.osmanthus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.event.EventListener;
import com.github.wei86609.osmanthus.rule.Flow;
import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.rule.Rule.TYPE;
import com.github.wei86609.osmanthus.rule.executor.RuleExecutor;
import com.github.wei86609.osmanthus.rule.intercepter.Intercepter;

public class FlowEngine{

    private final static Logger logger = Logger.getLogger(FlowEngine.class);

    private Map<TYPE,RuleExecutor> ruleExecutorMap;

    private final List<Intercepter> ruleIntercepters;

    private final List<EventListener> eventListeners;

    public FlowEngine(){
        ruleIntercepters=new ArrayList<Intercepter>();
        eventListeners=new ArrayList<EventListener>();
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
        try {
            //will trigger once the event initial
            initEvent(event);
            //get flow
            Flow flow=ConfigurationBuilder.getBuilder().getFlowById(event.getFlowId());
            if(flow==null){
                logger.error("Don't find any flow["+event.getFlowId()+"] the event["+event.getEventId()+"]");
                return false;
            }
            Rule firstRule=flow.getStartRule();
            if(firstRule==null){
                logger.error("Don't find one start rule of the flow["+event.getFlowId()+"] in the event["+event.getEventId()+"]");
                return false;
            }
            if(StringUtils.isBlank(event.getCurrentRuleId())){
                event.setCurrentRuleId(firstRule.getId());
                logger.debug("Rule is blank, will get the first rule ["+firstRule.getId()+"] of flow to execute.");
            }
            runFlowRule(event,event.getCurrentRuleId());
            //will trigger once the event complete
            completeEvent(event);
        } catch (Exception e) {
            this.exceptionEvent(event, e);
            throw e;
        }
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

    private void exceptionEvent(Event event, Exception e) {
        for(EventListener eventListener :eventListeners){
            if(eventListener!=null){
                eventListener.exception(event,e);
            }
        }
    }

    private void initEvent(Event event) {
        for(EventListener eventListener :eventListeners){
            if(eventListener!=null){
                eventListener.init(event);
            }
        }
    }

    private void completeEvent(Event event) {
        for(EventListener eventListener :eventListeners){
            if(eventListener!=null){
                eventListener.complete(event);
            }
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
            //will trigger once the event initial
            initEvent(event);
            //set current rule id
            event.setCurrentRuleId(ruleId);
            beforeIntercepter(event, rule);
            String nextRuleId= ruleExecutorMap.get(rule.getType()).execute(event, rule);
            logger.debug("Current rule["+ruleId+"]'s next rule id is["+nextRuleId+"]");
            afterIntercepter(event, rule);
            //will trigger once the event complete
            completeEvent(event);
        } catch (Exception e) {
            exceptionIntercepter(event, rule, e);
            exceptionEvent(event, e);
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
            //will trigger once the event initial
            initEvent(event);
            //set current rule id
            event.setCurrentRuleId(ruleId);
            beforeIntercepter(event, rule);
            String nextRuleId= ruleExecutorMap.get(rule.getType()).execute(event, rule);
            logger.debug("Current rule["+ruleId+"]'s next rule id is["+nextRuleId+"]");
            afterIntercepter(event, rule);
            //will trigger once the event complete
            completeEvent(event);
        } catch (Exception e) {
            exceptionIntercepter(event, rule, e);
            exceptionEvent(event, e);
            throw e;
        }
    }

}
