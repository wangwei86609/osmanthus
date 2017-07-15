package com.github.wei86609.osmanthus.intercepter;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.rule.Rule;

public interface Intercepter {

    public void before(Event event, Rule rule);

    public void after(Event event, Rule rule);

    public void exception(Exception e,Event event, Rule rule);
}
