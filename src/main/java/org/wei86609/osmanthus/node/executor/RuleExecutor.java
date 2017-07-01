package org.wei86609.osmanthus.node.executor;

import org.apache.log4j.Logger;
import org.mvel2.MVEL;
import org.wei86609.osmanthus.event.Event;
import org.wei86609.osmanthus.node.Node;
import org.wei86609.osmanthus.node.Node.TYPE;
import org.wei86609.osmanthus.node.Rule;

public class RuleExecutor implements NodeExecutor {

    private final static Logger logger = Logger.getLogger(RuleExecutor.class);

    public TYPE getType() {
        return TYPE.RULE;
    }

    public Boolean execute(Event event,Node node) throws Exception{
        Rule rule=(Rule)node;
        boolean success=executeCondition(rule,event);
        if(success){
            logger.debug("The node["+node.getId()+"] of the flow["+event.getEventId()+"] condition=["+rule.getCondition()+"] is true and action=["+rule.getAction()+"]");
            executeAction(rule,event);
        }
        return success;
    }

    protected boolean executeCondition(Rule rule, Event event) {
        return (Boolean)MVEL.eval(rule.getCondition(), event.getVars());
    }

    protected void executeAction(Rule rule, Event event) {
        MVEL.eval(rule.getAction(), event.getVars());
    }

    public void stop() {

    }

}
