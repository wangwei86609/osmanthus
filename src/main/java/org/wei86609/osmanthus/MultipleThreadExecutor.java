package org.wei86609.osmanthus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.wei86609.osmanthus.event.Event;
import org.wei86609.osmanthus.event.EventListener;
import org.wei86609.osmanthus.node.executor.EmptyNodeExecutor;
import org.wei86609.osmanthus.node.executor.MergeNodeExecutor;
import org.wei86609.osmanthus.node.executor.ParallelRuleExecutor;
import org.wei86609.osmanthus.node.executor.RuleExecutor;
import org.wei86609.osmanthus.node.executor.SplitRuleExecutor;
import org.wei86609.osmanthus.node.executor.ruleset.GeneralRuleSetExecutor;
import org.wei86609.osmanthus.node.handler.GeneralRuleSetHandler;

public class MultipleThreadExecutor implements EventListener{
    
    private final static Logger logger = Logger.getLogger(MultipleThreadExecutor.class);

    private ExecutorService executorService;

    private FlowEngine flowEngine;
    
    private int threadCnt=10;

    public int getThreadCnt() {
        return threadCnt;
    }

    public void setThreadCnt(int threadCnt) {
        this.threadCnt = threadCnt;
    }

    public MultipleThreadExecutor(){
        flowEngine=buildFlowEngine();
        executorService=Executors.newFixedThreadPool(threadCnt);
    }

    private FlowEngine buildFlowEngine() {
        FlowEngine osmanthus = new FlowEngine();
        //RuleExceutors
        ParallelRuleExecutor parallelRuleExecutor=new ParallelRuleExecutor();
        parallelRuleExecutor.setEventListener(this);
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

    public void addMultiEvent(final Event event, final String nodeId){
        executorService.execute(new Runnable(){
            public void run() {
                try {
                    flowEngine.execute(event, nodeId);
                } catch (Exception e) {
                    logger.error("FlowEngine execute current event={"+event+"} error",e);
                }
            }
        });
    }

    public void stop(){
        if(executorService!=null){
            executorService.shutdown();
            executorService=null;
        }
    }


}
