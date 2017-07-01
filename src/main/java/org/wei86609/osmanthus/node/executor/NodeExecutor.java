package org.wei86609.osmanthus.node.executor;

import org.wei86609.osmanthus.Executor;
import org.wei86609.osmanthus.event.Event;
import org.wei86609.osmanthus.node.Node;
import org.wei86609.osmanthus.node.Node.TYPE;

public interface NodeExecutor extends Executor<Node,Boolean>{

    Boolean execute(Event context,Node node) throws Exception;

    TYPE getType();

    void stop();
}
