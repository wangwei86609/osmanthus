package com.github.wei86609.osmanthus;

import junit.framework.TestCase;

import com.github.wei86609.osmanthus.event.DefaultEventListener;
import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.rule.executor.EndNodeExecutor;
import com.github.wei86609.osmanthus.rule.executor.MvelRuleExecutor;
import com.github.wei86609.osmanthus.rule.executor.SplitRuleExecutor;
import com.github.wei86609.osmanthus.rule.executor.StartNodeExecutor;
import com.github.wei86609.osmanthus.rule.executor.ruleset.GeneralRuleSetExecutor;
import com.github.wei86609.osmanthus.rule.handler.GeneralRuleSetHandler;
import com.github.wei86609.osmanthus.rule.intercepter.DefaultIntercepter;

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
    }*/

    protected FlowEngine buildSingleThreadFlowEngine() {
        FlowEngine engine = new FlowEngine();
        //add event listener and intercepter
        engine.addEventListener(new DefaultEventListener());
        engine.addRuleInterceptor(new DefaultIntercepter());
        // add RuleExceutors
        MvelRuleExecutor ruleExecutor = new MvelRuleExecutor();
        engine.addRuleExecutor(new SplitRuleExecutor());
        engine.addRuleExecutor(new StartNodeExecutor());
        engine.addRuleExecutor(new EndNodeExecutor());
        engine.addRuleExecutor(ruleExecutor);
        // add RuleSetExcecutors
        GeneralRuleSetExecutor generalRuleSetExecutor = new GeneralRuleSetExecutor();
        generalRuleSetExecutor.setRuleExecutor(ruleExecutor);
        generalRuleSetExecutor.addRuleSetHandler(new GeneralRuleSetHandler());
        engine.addRuleExecutor(generalRuleSetExecutor);
        return engine;
    }


    public void testExecute() {
        Event event=new Event();
        event.setFlowId("singleflow1");
        event.setEventId("eventid");
        event.add("salary", 5000);
        event.add("weight", 500);
        event.add("isBlackName", true);
        event.add("fee", 500);
        event.add("name", "test");
        event.add("reg", "12312");
        event.add("idNum", "350823198809122917");
        try {
            buildSingleThreadFlowEngine().execute(event);

            System.out.println(""+event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
