package com.github.wei86609.osmanthus.event;

public interface EventListener {

    public void init(Event event);

    public void complete(Event event);

    public void exception(Event event,Exception e);
}
