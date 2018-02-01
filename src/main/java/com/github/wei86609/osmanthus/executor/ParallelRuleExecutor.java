package com.github.wei86609.osmanthus.executor;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.event.ParallelEventIntercepter;
import com.github.wei86609.osmanthus.rule.Parallel;
import com.github.wei86609.osmanthus.rule.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;


public class ParallelRuleExecutor extends RuleExecutor {

    private ParallelEventIntercepter parallelEventListener;

    private ExecutorService threadPool;

    @Override
    public String run(Event event, Rule rule) {
        Parallel parallel = (Parallel) rule;
        List<Rule> lines = parallel.getRules();
        ArrayList<FutureTask<String>> tasks = new ArrayList<FutureTask<String>>();
        ArrayList<Event> newEvents = new ArrayList<Event>();
        for (Rule line : lines) {
            Event newEvent = event.copy();
            tasks.add(executeLine(newEvent, line));
            newEvents.add(newEvent);
        }
        return null;
    }

    private FutureTask<String> executeLine(final Event event, final Rule line) {
        FutureTask<String> task = new FutureTask<String>(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        if (ParallelRuleExecutor.this.parallelEventListener != null) {
                            ParallelRuleExecutor.this.parallelEventListener
                                    .startNewEvent(event, line.getToRuleId());
                            return "success";
                        }
                        return "failure";
                    }
                });
        if (this.threadPool != null) {
            this.threadPool.submit(task);
        }
        return task;
    }

    @Override
    public Rule.RuleType getType() {
        return Rule.RuleType.PARALLEL;
    }

    @Override
    public void stop() {
        if (this.threadPool != null) {
            this.threadPool.shutdown();
        }
    }

    public void setThreadPool(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    public void setParallelEventListener(
            ParallelEventIntercepter parallelEventListener) {
        this.parallelEventListener = parallelEventListener;
    }

}
