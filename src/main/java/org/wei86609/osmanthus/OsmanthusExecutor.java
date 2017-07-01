package org.wei86609.osmanthus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.wei86609.osmanthus.event.Event;
import org.wei86609.osmanthus.node.executor.EmptyNodeExecutor;
import org.wei86609.osmanthus.node.executor.ParallelRuleExecutor;
import org.wei86609.osmanthus.node.executor.RuleExecutor;
import org.wei86609.osmanthus.node.executor.SplitRuleExecutor;
import org.wei86609.osmanthus.node.executor.ruleset.CardRuleSetExecutor;
import org.wei86609.osmanthus.node.executor.ruleset.GeneralRuleSetExecutor;
import org.wei86609.osmanthus.node.handler.GeneralRuleSetHandler;

public class OsmanthusExecutor implements EventListener{

    private ExecutorService executorService = Executors.newCachedThreadPool();

    private FlowEngine buildFlowEngine() {
        FlowEngine osmanthus = new FlowEngine();
        // add RuleExceutors
        ParallelRuleExecutor parallelRuleExecutor=new ParallelRuleExecutor();
        parallelRuleExecutor.setEventListener(this);
        RuleExecutor ruleExecutor = new RuleExecutor();
        osmanthus.addNodeExecutor(new SplitRuleExecutor());
        osmanthus.addNodeExecutor(new EmptyNodeExecutor());
        osmanthus.addNodeExecutor(parallelRuleExecutor);
        osmanthus.addNodeExecutor(ruleExecutor);
        // add RuleSetExcecutors
        GeneralRuleSetHandler generalRuleSetHandler = new GeneralRuleSetHandler();
        GeneralRuleSetExecutor generalRuleSetExecutor = new GeneralRuleSetExecutor();
        generalRuleSetExecutor.setRuleExecutor(ruleExecutor);
        generalRuleSetExecutor.addRuleSetHandler(generalRuleSetHandler);
        osmanthus.addNodeExecutor(generalRuleSetExecutor);
        CardRuleSetExecutor cardRuleSetExecutor = new CardRuleSetExecutor();
        cardRuleSetExecutor.setRuleExecutor(ruleExecutor);
        cardRuleSetExecutor.addRuleSetHandler(generalRuleSetHandler);
        osmanthus.addNodeExecutor(cardRuleSetExecutor);
        return osmanthus;
    }

    public void newEvent(Event event, String nodeId) {
        OsmanthusTask task=new OsmanthusTask();
        task.setEvent(event);
        task.setNodeId(nodeId);
        task.setFlowEngine(buildFlowEngine());
        executorService.execute(task);
    }

    public boolean stop(){
        if(executorService.isTerminated()){
            System.out.println("Thread pool is terminated, will shutdown");
            executorService.shutdown();
            return true;
        }
        return false;
    }


}
