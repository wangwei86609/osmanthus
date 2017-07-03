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

    private ExecutorService executorService = Executors.newCachedThreadPool();

    private FlowEngine flowEngine;

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
    
    

    public void newEvent(Event event, String nodeId) {
        if(flowEngine==null){
            flowEngine=buildFlowEngine();
        }
        OsmanthusTask task=new OsmanthusTask();
        task.setEvent(event);
        task.setNodeId(nodeId);
        task.setFlowEngine(flowEngine);
        executorService.execute(task);
    }

    public void executeRule(Event event,String ruleId) throws Exception {
        if(flowEngine==null){
            flowEngine=buildFlowEngine();
        }
        flowEngine.runRule(event, ruleId);
    }

    public boolean stop(){
        executorService.shutdown();
        while(true){
            if(executorService.isTerminated()){
                return true;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
