package com.github.wei86609.osmanthus;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.node.executor.EmptyNodeExecutor;
import com.github.wei86609.osmanthus.node.executor.MergeNodeExecutor;
import com.github.wei86609.osmanthus.node.executor.ParallelRuleExecutor;
import com.github.wei86609.osmanthus.node.executor.RuleExecutor;
import com.github.wei86609.osmanthus.node.executor.SplitRuleExecutor;
import com.github.wei86609.osmanthus.node.executor.ruleset.GeneralRuleSetExecutor;
import com.github.wei86609.osmanthus.node.handler.GeneralRuleSetHandler;

import junit.framework.TestCase;

public class FlowEngineTest extends TestCase {

    protected FlowEngine buildMultiFlowEngine() {
        FlowEngine osmanthus = new FlowEngine();
        //RuleExceutors
        ParallelRuleExecutor parallelRuleExecutor=new ParallelRuleExecutor();
        parallelRuleExecutor.setEventListener(null);
        RuleExecutor ruleExecutor = new RuleExecutor();
        osmanthus.addNodeExecutor(new SplitRuleExecutor());
        osmanthus.addNodeExecutor(new EmptyNodeExecutor());
        osmanthus.addNodeExecutor(new MergeNodeExecutor());
        osmanthus.addNodeExecutor(parallelRuleExecutor);
        osmanthus.addNodeExecutor(ruleExecutor);
        //RuleSetExcecutors
        GeneralRuleSetHandler generalRuleSetHandler = new GeneralRuleSetHandler();
        GeneralRuleSetExecutor generalRuleSetExecutor = new GeneralRuleSetExecutor();
        generalRuleSetExecutor.setRuleExecutor(ruleExecutor);
        generalRuleSetExecutor.addRuleSetHandler(generalRuleSetHandler);
        osmanthus.addNodeExecutor(generalRuleSetExecutor);
        return osmanthus;
    }
    
    protected FlowEngine buildFlowEngine() {
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
    
    
    public void testExecute() {
        Event event=new Event();
        event.setFlowId("singleflow1");
        event.add("salary", 5000);
        event.add("weight", 500);
        event.add("isBlackName", true);
        event.add("fee", 500);
        event.add("name", "test");
        event.add("reg", "12312");
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
