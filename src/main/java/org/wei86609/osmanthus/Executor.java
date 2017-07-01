package org.wei86609.osmanthus;

import org.wei86609.osmanthus.event.Event;

public interface Executor<INPUT,OUTPUT> {

    public OUTPUT execute(Event context,INPUT input) throws Exception;
}
