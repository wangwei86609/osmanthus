package com.github.wei86609.osmanthus.event;

public class DefaultEventListener implements EventListener{

    public void init(Event event) {
        event.setStartTime(System.currentTimeMillis());
    }

    public void complete(Event event) {
        event.setEndTime(System.currentTimeMillis());
    }

    public void exception(Event event, Exception e) {
        event.setError(true);
    }

}
