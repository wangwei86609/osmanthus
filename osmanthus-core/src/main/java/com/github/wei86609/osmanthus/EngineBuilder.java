/*
 * Copyright (c) 2017 PaiPai Credit Data Services (Shanghai) Co., Ltd All Rights Reserved.
 */

package com.github.wei86609.osmanthus;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.event.ParallelEventIntercepter;
import com.github.wei86609.osmanthus.executor.*;

/**
 * @author wangwei19
 */
public class EngineBuilder {

    private volatile static EngineBuilder builder;

    private EngineBuilder() {
    }

    public static EngineBuilder build() {
        if (builder == null) {
            synchronized (EngineBuilder.class) {
                if (builder == null) {
                    builder = new EngineBuilder();
                }
            }
        }
        return builder;
    }

    public RuleSetEngine getRuleSetEngine() {
        final RuleSetEngine engine = new RuleSetEngine();
        engine.addRuleExecutor(new SplitRuleExecutor());
        engine.addRuleExecutor(new StartRuleExecutor());
        engine.addRuleExecutor(new EndRuleExecutor());
        engine.addRuleExecutor(new RuleExecutor());
        return engine;
    }

    /**
     * Please don't use it now
     *
     * @return RuleEngine
     */
    public RuleSetEngine multiThreadFlowEngine() {
        final RuleSetEngine engine = new RuleSetEngine();
        // RuleExceutors
        ParallelRuleExecutor parallelRuleExecutor = new ParallelRuleExecutor();
        engine.addRuleExecutor(new SplitRuleExecutor());
        engine.addRuleExecutor(new StartRuleExecutor());
        engine.addRuleExecutor(new MergeRuleExecutor());
        engine.addRuleExecutor(new EndRuleExecutor());
        engine.addRuleExecutor(new RuleExecutor());
        engine.addRuleExecutor(parallelRuleExecutor);
        // add event listener & thread pool for executer of parallel rule
        parallelRuleExecutor
                .setParallelEventListener(new ParallelEventIntercepter() {
                    @Override
                    public void startNewEvent(Event event, String ruleId)
                            throws Exception {
                        event.setCurrentRuleId(ruleId);
                        engine.execute(event, null);
                    }
                });
        // parallelRuleExecutor.setThreadPool(Executors.newCachedThreadPool());
        return engine;
    }
}
