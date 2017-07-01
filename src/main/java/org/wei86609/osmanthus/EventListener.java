package org.wei86609.osmanthus;

import org.wei86609.osmanthus.event.Event;

public interface EventListener {

    public void newEvent(Event event,String nodeId);

}
