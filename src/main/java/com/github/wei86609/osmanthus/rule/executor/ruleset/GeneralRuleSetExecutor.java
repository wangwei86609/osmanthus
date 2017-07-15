package com.github.wei86609.osmanthus.rule.executor.ruleset;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.rule.Rule.TYPE;
import com.github.wei86609.osmanthus.rule.executor.MvelRuleExecutor;
import com.github.wei86609.osmanthus.rule.executor.RuleExecutor;
import com.github.wei86609.osmanthus.rule.handler.RuleSetHandler;
import com.github.wei86609.osmanthus.rule.ruleset.RuleSet;

public class GeneralRuleSetExecutor implements RuleExecutor{

    private final static Logger logger = Logger.getLogger(GeneralRuleSetExecutor.class);

    private MvelRuleExecutor ruleExecutor;

    private List<RuleSetHandler> ruleSetHandlers;

    public String execute(Event event,Rule rule) throws Exception{
        RuleSet ruleSet=(RuleSet)rule;
        for(int i=0;i<ruleSetHandlers.size();i++){
            RuleSetHandler ruleSetHandler=ruleSetHandlers.get(i);
            if(ruleSetHandler.accept(ruleSet)){
                ruleSetHandler.handle(ruleSet);
            }
        }
        logger.debug("The ruleset["+ruleSet.getId()+"] of the event{"+event+"} has ["+ruleSet.getRules().size()+"] rules");
        run(event,ruleSet.getRules());
        return ruleSet.getToRuleId();
    }

    /**
     * execute all rules by recursion
     * @param event
     * @param ruleList
     */
    protected void run(Event event, List<Rule> ruleList) {
       if(ruleList==null ||ruleList.isEmpty()){
           return;
       }
       Rule rule=ruleList.get(0);
       if(rule==null){
           return;
       }
       boolean success=false;
       try {
           String res=ruleExecutor.execute(event,rule);
           if(StringUtils.isNotEmpty(res)){
               success=true;
           }
       } catch (Exception e) {
           logger.error("The rule["+rule.getId()+"] of the event ["+event.getEventId()+"] occurs exception.",e);
           if(!ruleList.isEmpty()){
               ruleList.remove(0);
           }
       }
       if (success && rule.isExclusive()) {
           logger.debug("The rule["+rule.getId()+"] of the event ["+event.getEventId()+"] is exclusive, remain rules will not be executed.");
          return;
       }

       if (rule.getMultipleTimes()<=0 && !ruleList.isEmpty()) {
           ruleList.remove(0);
       }else{
           int times=rule.getMultipleTimes();
           rule.setMultipleTimes(--times);
       }

       run(event,ruleList);
    }

    public void addRuleSetHandler(RuleSetHandler ruleSetHandler){
        if(ruleSetHandlers==null){
            ruleSetHandlers=new ArrayList<RuleSetHandler>();
        }
        ruleSetHandlers.add(ruleSetHandler);
    }

    public List<RuleSetHandler> getRuleSetHandlers() {
        return ruleSetHandlers;
    }

    public void setRuleSetHandlers(List<RuleSetHandler> ruleSetHandlers) {
        this.ruleSetHandlers = ruleSetHandlers;
    }

    public TYPE getType() {
        return TYPE.SET;
    }

    public MvelRuleExecutor getRuleExecutor() {
        return ruleExecutor;
    }

    public void setRuleExecutor(MvelRuleExecutor ruleExecutor) {
        this.ruleExecutor = ruleExecutor;
    }

    public void stop() {

    }

}
