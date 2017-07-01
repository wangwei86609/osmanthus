package org.wei86609.osmanthus.node.executor;

import org.apache.log4j.Logger;
import org.mvel2.MVEL;
import org.wei86609.osmanthus.event.Event;
import org.wei86609.osmanthus.node.Merge;
import org.wei86609.osmanthus.node.Node;
import org.wei86609.osmanthus.node.Node.TYPE;

public class MergeNodeExecutor implements NodeExecutor{

    private final static Logger logger = Logger.getLogger(MergeNodeExecutor.class);

    public String execute(Event event, Node node) throws Exception {
        Merge merge=(Merge)node;
        logger.debug("Merge["+node.getId()+"] of the event {"+event+"} has ["+merge.getLineCnt()+"] threads need to merge");
        if(merge.canMerge(event)){
            logger.debug("Merge["+node.getId()+"] of the event {"+event+"} will merge all threads");
            return merge.getToNodeId();
        }
        return null;
    }

    protected boolean executeCondition(String condition, Event context) {
        return (Boolean)MVEL.eval(condition, context.getVars());
    }

    public TYPE getType() {
        return TYPE.MERGE;
    }

    public void stop() {

    }
}
