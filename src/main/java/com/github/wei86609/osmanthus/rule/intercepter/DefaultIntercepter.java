package com.github.wei86609.osmanthus.rule.intercepter;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.rule.Rule;

public class DefaultIntercepter implements Intercepter{

    public void before(Event event, Rule rule) {
        rule.setStartTime(System.currentTimeMillis());
    }

    public void after(Event event, Rule rule) {
        rule.setEndTime(System.currentTimeMillis());
    }

    public void exception(Exception e, Event event, Rule rule) {
        event.setError(true);
    }

}
