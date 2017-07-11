package com.github.wei86609.osmanthus.node.executor;

import java.util.List;

import org.apache.log4j.Logger;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.event.EventListener;
import com.github.wei86609.osmanthus.event.Supervisor;
import com.github.wei86609.osmanthus.node.Line;
import com.github.wei86609.osmanthus.node.Node;
import com.github.wei86609.osmanthus.node.Node.TYPE;
import com.github.wei86609.osmanthus.node.Parallel;

public class ParallelRuleExecutor extends NodeExecutor{

    private final static Logger logger = Logger.getLogger(ParallelRuleExecutor.class);

    private EventListener eventListener;

    @Override
    public String run(Event event, Node node,Supervisor supervisor) throws Exception {
        Parallel parallel=(Parallel)node;
        logger.debug("Parallel["+node.getId()+"] of the event {"+event.getEventId()+"} has ["+parallel.getLines().size()+"] thread lines");
        List<Line> lines=parallel.getLines();
        for(Line line:lines){
            if(eventListener!=null){
                eventListener.addMultiEvent(event.createNewEvent(), line.getToNodeId());
            }
        }
        return null;
    }

    @Override
    public TYPE getType() {
        return TYPE.PARALLEL;
    }

    public EventListener getEventListener() {
        return eventListener;
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

}
