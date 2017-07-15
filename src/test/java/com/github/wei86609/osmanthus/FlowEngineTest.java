package com.github.wei86609.osmanthus;

import java.util.concurrent.Executors;

import junit.framework.TestCase;

import com.github.wei86609.osmanthus.event.DefaultEventListener;
import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.event.ParallelEventListener;
import com.github.wei86609.osmanthus.rule.executor.EndNodeExecutor;
import com.github.wei86609.osmanthus.rule.executor.MergeNodeExecutor;
import com.github.wei86609.osmanthus.rule.executor.MvelRuleExecutor;
import com.github.wei86609.osmanthus.rule.executor.ParallelRuleExecutor;
import com.github.wei86609.osmanthus.rule.executor.SplitRuleExecutor;
import com.github.wei86609.osmanthus.rule.executor.StartNodeExecutor;
import com.github.wei86609.osmanthus.rule.executor.ruleset.GeneralRuleSetExecutor;
import com.github.wei86609.osmanthus.rule.handler.GeneralRuleSetHandler;
import com.github.wei86609.osmanthus.rule.intercepter.DefaultIntercepter;

public class FlowEngineTest extends TestCase {

   protected FlowEngine buildMultiThreadFlowEngine() {
        final FlowEngine engine = new FlowEngine();
        //RuleExceutors
        ParallelRuleExecutor parallelRuleExecutor=new ParallelRuleExecutor();
        MvelRuleExecutor ruleExecutor = new MvelRuleExecutor();
        engine.addRuleExecutor(new SplitRuleExecutor());
        engine.addRuleExecutor(new StartNodeExecutor());
        engine.addRuleExecutor(new MergeNodeExecutor());
        engine.addRuleExecutor(new EndNodeExecutor());
        engine.addRuleExecutor(parallelRuleExecutor);
        engine.addRuleExecutor(ruleExecutor);
        //RuleSetExcecutors
        GeneralRuleSetExecutor generalRuleSetExecutor = new GeneralRuleSetExecutor();
        generalRuleSetExecutor.setRuleExecutor(ruleExecutor);
        generalRuleSetExecutor.addRuleSetHandler(new GeneralRuleSetHandler());
        engine.addRuleExecutor(generalRuleSetExecutor);
        //add event listener & thread pool for executer of parallel rule
        parallelRuleExecutor.setParallelEventListener(new ParallelEventListener(){
            public void startNewEvent(Event event, String ruleId) throws Exception{
                event.setCurrentRuleId(ruleId);
                engine.execute(event);
            }
        });
        parallelRuleExecutor.setThreadPool(Executors.newCachedThreadPool());
        return engine;
    }



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


    public void testSingleThreadExecute() {
        Event event=new Event();
        event.setFlowId("singleflow1");
        event.setEventId("eventid");
        event.addVar("salary", 5000);
        event.addVar("weight", 500);
        event.addVar("isBlackName", true);
        event.addVar("fee", 500);
        event.addVar("name", "test");
        event.addVar("reg", "12312");
        event.addVar("idNum", "350823198809122917");
        try {
            buildSingleThreadFlowEngine().execute(event);

            System.out.println(""+event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testMultiThreadExecute() {
        Event event=new Event();
        event.setFlowId("multiflow");
        event.setEventId("muleventid");
        event.addVar("salary", 5000);
        event.addVar("weight", 500);
        event.addVar("isBlackName", true);
        event.addVar("fee", 500);
        event.addVar("name", "test");
        event.addVar("reg", "12312");
        event.addVar("idNum", "350823198809122917");
        try {
            buildMultiThreadFlowEngine().execute(event);

            System.out.println(""+event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
