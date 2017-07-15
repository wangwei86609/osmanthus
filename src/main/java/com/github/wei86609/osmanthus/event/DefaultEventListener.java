package com.github.wei86609.osmanthus.event;

import org.apache.log4j.Logger;

public class DefaultEventListener implements EventListener{

    private final static Logger logger = Logger.getLogger(DefaultEventListener.class);

    public void init(Event event) {
        event.setStartTime(System.currentTimeMillis());
        logger.debug("Start to execute the event["+event.getEventId()+"]");
    }

    public void complete(Event event) {
        event.setEndTime(System.currentTimeMillis());
        logger.debug("Execute the event["+event.getEventId()+"] end up");
    }

    public void exception(Event event, Exception e) {
        event.setError(true);
        logger.error("Execute the event["+event.getEventId()+"] occurs exception",e);
    }

}
