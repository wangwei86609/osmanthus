package com.github.wei86609.osmanthus.node.executor;

import org.apache.log4j.Logger;
import org.mvel2.MVEL;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.event.Supervisor;
import com.github.wei86609.osmanthus.node.Merge;
import com.github.wei86609.osmanthus.node.Node;
import com.github.wei86609.osmanthus.node.Node.TYPE;

public class MergeNodeExecutor extends NodeExecutor{

    private final static Logger logger = Logger.getLogger(MergeNodeExecutor.class);

    @Override
    public String run(Event event, Node node,Supervisor supervisor) throws Exception {
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

    @Override
    public TYPE getType() {
        return TYPE.MERGE;
    }

}
