package org.wei86609.osmanthus.node.executor;

import org.apache.log4j.Logger;
import org.wei86609.osmanthus.event.Event;
import org.wei86609.osmanthus.node.Node;
import org.wei86609.osmanthus.node.Node.TYPE;

public class EmptyNodeExecutor implements NodeExecutor{

    private final static Logger logger = Logger.getLogger(EmptyNodeExecutor.class);

    public Boolean execute(Event event, Node node) throws Exception {
        logger.debug("The empty node["+node.getId()+"] of the flow["+event.getEventId()+"] executed.");
        return true;
    }

    public TYPE getType() {
        return TYPE.EMPTY;
    }

    public void stop() {

    }

}
