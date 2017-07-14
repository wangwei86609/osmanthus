package com.github.wei86609.osmanthus.rule.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.event.ParallelEventListener;
import com.github.wei86609.osmanthus.monitor.RuleInfo;
import com.github.wei86609.osmanthus.rule.Line;
import com.github.wei86609.osmanthus.rule.Parallel;
import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.rule.Rule.TYPE;

public class ParallelRuleExecutor extends CommonExecutor{

    private final static Logger logger = Logger.getLogger(ParallelRuleExecutor.class);

    private ParallelEventListener parallelEventListener;

    private ExecutorService threadPool;

    @Override
    public String run(Event event, Rule rule,RuleInfo ruleInfo) throws Exception {
        Parallel parallel=(Parallel)rule;
        logger.debug("Parallel["+parallel.getId()+"] of the event {"+event.getEventId()+"} has ["+parallel.getLines().size()+"] thread lines");
        List<Line> lines=parallel.getLines();
        ArrayList<FutureTask<String>> tasks=new ArrayList<FutureTask<String>>();
        ArrayList<Event> newEvents=new ArrayList<Event>();
        for(Line line:lines){
            Event newEvent=event.copy();
            tasks.add(executeLine(newEvent,line));
            newEvents.add(newEvent);
        }
        //if is sync, need to wait all threads run its event complete.
        if(parallel.isSync()){
            for(FutureTask<String> task:tasks){
                task.get(parallel.getTimeout(), TimeUnit.SECONDS);
            }
            for(Event eve:newEvents){
                event.addSubEvent(eve);
            }
            return rule.getToNodeId();
        }
        return null;
    }

    private FutureTask<String> executeLine(final Event event,final Line line){
        FutureTask<String> task=new FutureTask<String>(new Callable<String>(){
            public String call() throws Exception {
                if(parallelEventListener!=null){
                    parallelEventListener.startNewEvent(event, line.getToNodeId());
                    return "success";
                }
                return "failure";
            }
        });
        if(threadPool!=null){
            threadPool.submit(task);
        }
        return task;
    }

    @Override
    public TYPE getType() {
        return TYPE.PARALLEL;
    }

    public ExecutorService getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    public ParallelEventListener getParallelEventListener() {
        return parallelEventListener;
    }

    public void setParallelEventListener(ParallelEventListener parallelEventListener) {
        this.parallelEventListener = parallelEventListener;
    }

}
