package org.wei86609.osmanthus.node.executor.ruleset;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.wei86609.osmanthus.event.Event;
import org.wei86609.osmanthus.node.Node;
import org.wei86609.osmanthus.node.Node.TYPE;
import org.wei86609.osmanthus.node.Rule;
import org.wei86609.osmanthus.node.executor.NodeExecutor;
import org.wei86609.osmanthus.node.executor.RuleExecutor;
import org.wei86609.osmanthus.node.handler.RuleSetHandler;
import org.wei86609.osmanthus.node.ruleset.GeneralRuleSet;

public class GeneralRuleSetExecutor implements NodeExecutor{

    private final static Logger logger = Logger.getLogger(GeneralRuleSetExecutor.class);

    private RuleExecutor ruleExecutor;

    private List<RuleSetHandler> ruleSetHandlers;

    public Boolean execute(Event event,Node node) throws Exception{
        GeneralRuleSet ruleSet=(GeneralRuleSet)node;
        List<Rule> rules=ruleSet.getRules();
        for(int i=0;i<ruleSetHandlers.size();i++){
            RuleSetHandler ruleSetHandler=ruleSetHandlers.get(i);
            if(ruleSetHandler.accept(ruleSet)){
                rules=ruleSetHandler.handle(ruleSet.getRules());
            }
        }
        logger.debug("The ruleset["+node.getId()+"] of the flow["+event.getEventId()+"] has ["+ruleSet.getRules().size()+"] rules");
        run(event,rules);
        return true;
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
           success=ruleExecutor.execute(event,rule);
       } catch (Exception e) {
           logger.error("The node["+rule.getId()+"] of the flow["+event.getEventId()+"] occurs exception.",e);
           if(!ruleList.isEmpty()){
               ruleList.remove(0);
           }
       }
       if (success && rule.isExclusive()) {
           logger.debug("The node["+rule.getId()+"] of the flow["+event.getEventId()+"] is exclusive, remain nodes will not be executed.");
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

    public RuleExecutor getRuleExecutor() {
        return ruleExecutor;
    }

    public void setRuleExecutor(RuleExecutor ruleExecutor) {
        this.ruleExecutor = ruleExecutor;
    }

    public void stop() {

    }

}
