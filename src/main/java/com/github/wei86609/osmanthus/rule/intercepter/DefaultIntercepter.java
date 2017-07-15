package com.github.wei86609.osmanthus.rule.intercepter;

import org.apache.log4j.Logger;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.rule.Rule;

public class DefaultIntercepter implements Intercepter{

    private final static Logger logger = Logger.getLogger(DefaultIntercepter.class);

    public void before(Event event, Rule rule) {
        rule.setStartTime(System.currentTimeMillis());
        logger.debug("Start to execute rule["+rule.getId()+"]");
    }

    public void after(Event event, Rule rule) {
        rule.setEndTime(System.currentTimeMillis());
        logger.debug("Execute rule["+rule.getId()+"] end up");
    }

    public void exception(Exception e, Event event, Rule rule) {
        event.setError(true);
        logger.debug("Execute rule["+rule.getId()+"] occurs exception", e);
    }

}
