package org.wei86609.osmanthus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.wei86609.osmanthus.event.Event;
import org.wei86609.osmanthus.event.EventListener;
import org.wei86609.osmanthus.node.executor.EmptyNodeExecutor;
import org.wei86609.osmanthus.node.executor.MergeNodeExecutor;
import org.wei86609.osmanthus.node.executor.ParallelRuleExecutor;
import org.wei86609.osmanthus.node.executor.RuleExecutor;
import org.wei86609.osmanthus.node.executor.SplitRuleExecutor;
import org.wei86609.osmanthus.node.executor.ruleset.GeneralRuleSetExecutor;
import org.wei86609.osmanthus.node.handler.GeneralRuleSetHandler;

public class OsmanthusExecutor implements EventListener{
    
    private volatile static OsmanthusExecutor executor;

    private ExecutorService executorService;

    private FlowEngine flowEngine;
    
    private int threadCnt=10;
    
    public int getThreadCnt() {
        return threadCnt;
    }

    public void setThreadCnt(int threadCnt) {
        this.threadCnt = threadCnt;
    }

    private OsmanthusExecutor(){
        flowEngine=buildFlowEngine();
    }

    private FlowEngine buildFlowEngine() {
        FlowEngine osmanthus = new FlowEngine();
        // add RuleExceutors
        ParallelRuleExecutor parallelRuleExecutor=new ParallelRuleExecutor();
        parallelRuleExecutor.setEventListener(this);
        RuleExecutor ruleExecutor = new RuleExecutor();
        osmanthus.addNodeExecutor(new SplitRuleExecutor());
        osmanthus.addNodeExecutor(new EmptyNodeExecutor());
        osmanthus.addNodeExecutor(new MergeNodeExecutor());
        osmanthus.addNodeExecutor(parallelRuleExecutor);
        osmanthus.addNodeExecutor(ruleExecutor);
        // add RuleSetExcecutors
        GeneralRuleSetHandler generalRuleSetHandler = new GeneralRuleSetHandler();
        GeneralRuleSetExecutor generalRuleSetExecutor = new GeneralRuleSetExecutor();
        generalRuleSetExecutor.setRuleExecutor(ruleExecutor);
        generalRuleSetExecutor.addRuleSetHandler(generalRuleSetHandler);
        osmanthus.addNodeExecutor(generalRuleSetExecutor);
        return osmanthus;
    }
    
    public OsmanthusExecutor withSingleThreadModel(){
        if(executorService!=null){
            executorService.shutdown();
            executorService=null;
        }
        return executor;
    }
    
    public OsmanthusExecutor withMultipleThreadModel(){
        if(executorService==null){
            executorService=Executors.newFixedThreadPool(threadCnt);
        }
        return executor;
    }
    
    public static OsmanthusExecutor getExecutor() throws Exception {
        if (executor == null) {
            synchronized (OsmanthusExecutor.class) {
                if (executor == null) {
                    executor = new OsmanthusExecutor();
                }
            }
        }
        return executor;
    }

    public void executeNewEvent(Event event, String nodeId) {
        withMultipleThreadModel();
        OsmanthusTask task=new OsmanthusTask();
        task.setEvent(event);
        task.setNodeId(nodeId);
        task.setFlowEngine(flowEngine);
        executorService.execute(task);
    }

    public void executeRule(Event event,String ruleId) throws Exception {
        flowEngine.runRule(event, ruleId);
    }

    public void stop(){
        if(executorService!=null){
            executorService.shutdown();
            executorService=null;
        }
    }


}
