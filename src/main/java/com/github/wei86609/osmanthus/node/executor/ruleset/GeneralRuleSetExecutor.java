package com.github.wei86609.osmanthus.node.executor.ruleset;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.event.Supervisor;
import com.github.wei86609.osmanthus.node.Node;
import com.github.wei86609.osmanthus.node.Node.TYPE;
import com.github.wei86609.osmanthus.node.Rule;
import com.github.wei86609.osmanthus.node.executor.NodeExecutor;
import com.github.wei86609.osmanthus.node.executor.RuleExecutor;
import com.github.wei86609.osmanthus.node.handler.RuleSetHandler;
import com.github.wei86609.osmanthus.node.ruleset.RuleSet;

public class GeneralRuleSetExecutor extends NodeExecutor{

    private final static Logger logger = Logger.getLogger(GeneralRuleSetExecutor.class);

    private RuleExecutor ruleExecutor;

    private List<RuleSetHandler> ruleSetHandlers;

    @Override
    public String run(Event event,Node node,Supervisor supervisor) throws Exception{
        RuleSet ruleSet=(RuleSet)node;
        for(int i=0;i<ruleSetHandlers.size();i++){
            RuleSetHandler ruleSetHandler=ruleSetHandlers.get(i);
            if(ruleSetHandler.accept(ruleSet)){
                ruleSetHandler.handle(ruleSet);
            }
        }
        logger.debug("The ruleset["+node.getId()+"] of the event{"+event+"} has ["+ruleSet.getRules().size()+"] rules");
        run(event,ruleSet.getRules());
        return ruleSet.getToNodeId();
    }

    /**
     * execute all rules by recursion
     * @param context
     * @param ruleSet
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
           logger.error("The node["+rule.getId()+"] of the event {"+event+"} occurs exception.",e);
           if(!ruleList.isEmpty()){
               ruleList.remove(0);
           }
       }
       if (success && rule.isExclusive()) {
           logger.debug("The node["+rule.getId()+"] of the event {"+event+"} is exclusive, remain nodes will not be executed.");
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

    @Override
    public TYPE getType() {
        return TYPE.SET;
    }

    public RuleExecutor getRuleExecutor() {
        return ruleExecutor;
    }

    public void setRuleExecutor(RuleExecutor ruleExecutor) {
        this.ruleExecutor = ruleExecutor;
    }


}
