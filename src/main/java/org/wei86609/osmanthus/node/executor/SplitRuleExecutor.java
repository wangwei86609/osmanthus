package org.wei86609.osmanthus.node.executor;

import org.apache.commons.lang3.StringUtils;
import org.mvel2.MVEL;
import org.wei86609.osmanthus.event.Event;
import org.wei86609.osmanthus.node.Constraint;
import org.wei86609.osmanthus.node.Node;
import org.wei86609.osmanthus.node.Node.TYPE;
import org.wei86609.osmanthus.node.Split;

public class SplitRuleExecutor implements NodeExecutor{

    public Boolean execute(Event context, Node node) throws Exception {
        Split split=(Split)node;
        for(Constraint c:split.getConstraints()){
            if(executeCondition(c.getCondition(),context)){
                split.setToNodeId(c.getToNodeId());
                return true;
            }
        }
        if(StringUtils.isEmpty(split.getToNodeId())){
            throw new Exception("Seems constraint's conditions all mismatch.");
        }
        return false;
    }

    protected boolean executeCondition(String condition, Event context) {
        return (Boolean)MVEL.eval(condition, context.getVars());
    }

    public TYPE getType() {
        return TYPE.SPLIT;
    }

    public void stop() {

    }

}
