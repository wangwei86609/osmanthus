package org.wei86609.osmanthus.node.executor;

import java.util.List;

import org.apache.log4j.Logger;
import org.wei86609.osmanthus.EventListener;
import org.wei86609.osmanthus.event.Event;
import org.wei86609.osmanthus.node.Line;
import org.wei86609.osmanthus.node.Node;
import org.wei86609.osmanthus.node.Node.TYPE;
import org.wei86609.osmanthus.node.Parallel;

public class ParallelRuleExecutor implements NodeExecutor{

    private final static Logger logger = Logger.getLogger(ParallelRuleExecutor.class);

    private EventListener eventListener;

    public Boolean execute(Event event, Node node) throws Exception {
        Parallel parallel=(Parallel)node;
        logger.debug("Parallel["+node.getId()+"] of the flow["+event.getEventId()+"] has ["+parallel.getLines().size()+"] thread lines");
        List<Line> lines=parallel.getLines();
        for(Line line:lines){
            if(eventListener!=null){
                eventListener.newEvent(event.createNewEvent(), line.getToNodeId());
            }
        }
        return true;
    }

    public TYPE getType() {
        return TYPE.PARALLEL;
    }

    public void stop() {

    }

    public EventListener getEventListener() {
        return eventListener;
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

}
