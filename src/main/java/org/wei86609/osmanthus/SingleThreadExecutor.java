package org.wei86609.osmanthus;

import org.wei86609.osmanthus.event.Event;
import org.wei86609.osmanthus.node.executor.EmptyNodeExecutor;
import org.wei86609.osmanthus.node.executor.RuleExecutor;
import org.wei86609.osmanthus.node.executor.SplitRuleExecutor;
import org.wei86609.osmanthus.node.executor.ruleset.GeneralRuleSetExecutor;
import org.wei86609.osmanthus.node.handler.GeneralRuleSetHandler;

public class SingleThreadExecutor{

    private FlowEngine flowEngine;

    public SingleThreadExecutor(){
        flowEngine=buildFlowEngine();
    }

    private FlowEngine buildFlowEngine() {
        FlowEngine osmanthus = new FlowEngine();
        // add RuleExceutors
        RuleExecutor ruleExecutor = new RuleExecutor();
        osmanthus.addNodeExecutor(new SplitRuleExecutor());
        osmanthus.addNodeExecutor(new EmptyNodeExecutor());
        osmanthus.addNodeExecutor(ruleExecutor);
        // add RuleSetExcecutors
        GeneralRuleSetHandler generalRuleSetHandler = new GeneralRuleSetHandler();
        GeneralRuleSetExecutor generalRuleSetExecutor = new GeneralRuleSetExecutor();
        generalRuleSetExecutor.setRuleExecutor(ruleExecutor);
        generalRuleSetExecutor.addRuleSetHandler(generalRuleSetHandler);
        osmanthus.addNodeExecutor(generalRuleSetExecutor);
        return osmanthus;
    }
    
    public void executeFlow(Event event) throws Exception {
        flowEngine.execute(event,null);
    }

    public void executeRuleFromFlow(Event event,String ruleId) throws Exception {
        flowEngine.runRule(event, ruleId);
    }
    
    public void executeSingleRule(Event event,String ruleId) throws Exception {
        flowEngine.runSingleRule(event, ruleId);
    }

}
