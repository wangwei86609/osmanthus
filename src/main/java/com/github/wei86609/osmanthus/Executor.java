package com.github.wei86609.osmanthus;

import com.github.wei86609.osmanthus.event.Event;

public interface Executor<INPUT,OUTPUT> {

    public OUTPUT execute(Event context,INPUT input);
}
