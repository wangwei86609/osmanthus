package org.wei86609.osmanthus.node.executor;

import org.apache.log4j.Logger;
import org.wei86609.osmanthus.event.Event;
import org.wei86609.osmanthus.event.Supervisor;
import org.wei86609.osmanthus.node.Node;
import org.wei86609.osmanthus.node.Node.TYPE;

public class EmptyNodeExecutor extends NodeExecutor{

    private final static Logger logger = Logger.getLogger(EmptyNodeExecutor.class);

    @Override
    public String run(Event event, Node node,Supervisor supervisor) throws Exception {
        logger.debug("The empty node["+node.getId()+"] of the event{"+event+"} executed.");
        return node.getToNodeId();
    }

    @Override
    public TYPE getType() {
        return TYPE.EMPTY;
    }

}
