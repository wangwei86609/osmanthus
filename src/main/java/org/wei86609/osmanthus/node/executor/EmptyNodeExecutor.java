package org.wei86609.osmanthus.node.executor;

import org.wei86609.osmanthus.event.Event;
import org.wei86609.osmanthus.node.Node;
import org.wei86609.osmanthus.node.Node.TYPE;

public class EmptyNodeExecutor implements NodeExecutor{

    public Boolean execute(Event context, Node node) throws Exception {
        return true;
    }

    public TYPE getType() {
        return TYPE.EMPTY;
    }

    public void stop() {

    }

}
