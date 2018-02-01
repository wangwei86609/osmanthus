package com.github.wei86609.osmanthus.event;

public interface ParallelEventIntercepter {

    public void startNewEvent(Event event, String ruleId) throws Exception;

}
