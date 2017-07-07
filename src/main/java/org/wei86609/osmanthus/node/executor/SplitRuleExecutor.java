package org.wei86609.osmanthus.node.executor;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.mvel2.MVEL;
import org.wei86609.osmanthus.event.Event;
import org.wei86609.osmanthus.event.Supervisor;
import org.wei86609.osmanthus.node.Constraint;
import org.wei86609.osmanthus.node.Node;
import org.wei86609.osmanthus.node.Node.TYPE;
import org.wei86609.osmanthus.node.Split;

public class SplitRuleExecutor extends NodeExecutor{

    private final static Logger logger = Logger.getLogger(SplitRuleExecutor.class);

    @Override
    public String run(Event event, Node node,Supervisor supervisor) throws Exception {
        Split split=(Split)node;
        logger.debug("Split["+node.getId()+"] of the event {"+event+"} has ["+split.getConstraints().size()+"] Constraints");
        for(Constraint c:split.getConstraints()){
            if(executeCondition(c.getCondition(),event)){
                supervisor.setCondition(c.getCondition());
                supervisor.setResultOfConditoin(true);
                logger.debug("Split["+node.getId()+"] of the event {"+event+"} Constraint's condition ["+c.getCondition()+"] result is true, will link to Node["+c.getToNodeId()+"].");
                return c.getToNodeId();
            }
        }
        if(StringUtils.isEmpty(split.getToNodeId())){
            logger.error("The node["+node.getId()+"] of the event {"+event+"} Seems constraint's conditions are all mismatch.");
            throw new Exception("The node["+node.getId()+"] of the event {"+event+"} Seems constraint's conditions are all mismatch.");
        }
        return null;
    }

    protected boolean executeCondition(String condition, Event context) {
        return (Boolean)MVEL.eval(condition, context.getVars());
    }

    @Override
    public TYPE getType() {
        return TYPE.SPLIT;
    }

}
