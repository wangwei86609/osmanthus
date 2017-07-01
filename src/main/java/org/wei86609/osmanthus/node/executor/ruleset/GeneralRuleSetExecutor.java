package org.wei86609.osmanthus.node.executor.ruleset;

import java.util.ArrayList;
import java.util.List;

import org.wei86609.osmanthus.event.Event;
import org.wei86609.osmanthus.node.Node;
import org.wei86609.osmanthus.node.Node.TYPE;
import org.wei86609.osmanthus.node.Rule;
import org.wei86609.osmanthus.node.executor.NodeExecutor;
import org.wei86609.osmanthus.node.executor.RuleExecutor;
import org.wei86609.osmanthus.node.handler.RuleSetHandler;
import org.wei86609.osmanthus.node.ruleset.GeneralRuleSet;

public class GeneralRuleSetExecutor implements NodeExecutor{

    private final static String RULESET_MODEL_KEY="ascan";

    private RuleExecutor ruleExecutor;

    private List<RuleSetHandler> ruleSetHandlers;

    public Boolean execute(Event context,Node node) throws Exception{
        GeneralRuleSet ruleSet=(GeneralRuleSet)node;
        List<Rule> rules=ruleSet.getRules();
        for(int i=0;i<ruleSetHandlers.size();i++){
            RuleSetHandler ruleSetHandler=ruleSetHandlers.get(i);
            if(ruleSetHandler.accept(ruleSet)){
                rules=ruleSetHandler.handle(ruleSet.getRules());
            }
        }
        context.add(RULESET_MODEL_KEY, GeneralRuleSet.LAST_MODEL.equalsIgnoreCase(ruleSet.getModel())?true:false);
        run(context,rules);
        return true;
    }

    /**
     * execute all rules by recursion
     * @param context
     * @param ruleSet
     */
    protected void run(Event context, List<Rule> ruleList) {
       if(ruleList==null ||ruleList.isEmpty()){
           return;
       }
       Rule rule=ruleList.get(0);
       if(rule==null){
           return;
       }
       try {
           ruleExecutor.execute(context,rule);
       } catch (Exception e) {
           e.printStackTrace();
           if(!ruleList.isEmpty()){
               ruleList.remove(0);
           }
           if((Boolean)context.get(RULESET_MODEL_KEY)){
               return;
           }
       }
       if (rule.isExclusive()) {
          return;
       }

       if (rule.getMultipleTimes()<=0) {
           if(!ruleList.isEmpty()){
               ruleList.remove(0);
           }
       }else{
           int times=rule.getMultipleTimes();
           rule.setMultipleTimes(--times);
       }
       run(context,ruleList);
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

    public void stop() {

    }

}
