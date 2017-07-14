package com.github.wei86609.osmanthus;

import junit.framework.TestCase;

import com.github.wei86609.osmanthus.event.Event;

public class FlowEngineTest extends TestCase {

/*    protected FlowEngine buildMultiFlowEngine() {
        FlowEngine osmanthus = new FlowEngine();
        //RuleExceutors
        ParallelRuleExecutor parallelRuleExecutor=new ParallelRuleExecutor();
        parallelRuleExecutor.setParallelEventListener(null);
        MvelRuleExecutor ruleExecutor = new MvelRuleExecutor();
        osmanthus.addNodeExecutor(new SplitRuleExecutor());
        osmanthus.addNodeExecutor(new StartNodeExecutor());
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
        MvelRuleExecutor ruleExecutor = new MvelRuleExecutor();
        osmanthus.addNodeExecutor(new SplitRuleExecutor());
        osmanthus.addNodeExecutor(new StartNodeExecutor());
        osmanthus.addNodeExecutor(ruleExecutor);
        // add RuleSetExcecutors
        GeneralRuleSetHandler generalRuleSetHandler = new GeneralRuleSetHandler();
        GeneralRuleSetExecutor generalRuleSetExecutor = new GeneralRuleSetExecutor();
        generalRuleSetExecutor.setRuleExecutor(ruleExecutor);
        generalRuleSetExecutor.addRuleSetHandler(generalRuleSetHandler);
        osmanthus.addNodeExecutor(generalRuleSetExecutor);
        return osmanthus;
    }
*/

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
