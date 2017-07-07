package org.wei86609.osmanthus.node.executor;

import org.apache.log4j.Logger;
import org.mvel2.MVEL;
import org.wei86609.osmanthus.event.Event;
import org.wei86609.osmanthus.event.Supervisor;
import org.wei86609.osmanthus.node.Node;
import org.wei86609.osmanthus.node.Node.TYPE;
import org.wei86609.osmanthus.node.Rule;

public class RuleExecutor extends NodeExecutor {

    private final static Logger logger = Logger.getLogger(RuleExecutor.class);

    @Override
    public TYPE getType() {
        return TYPE.RULE;
    }

    @Override
    public String run(Event event,Node node,Supervisor supervisor) throws Exception{
        Rule rule=(Rule)node;
        supervisor.setCondition(rule.getCondition());
        supervisor.setAction(rule.getAction());
        boolean success=executeCondition(rule,event);
        if(success){
            supervisor.setResultOfConditoin(success);
            logger.debug("The node["+node.getId()+"] of the event {"+event.getEventId()+"} condition=["+rule.getCondition()+"] is true and action=["+rule.getAction()+"]");
            Object obj=executeAction(rule,event);
            supervisor.setResultOfAction(obj);
            return node.getToNodeId();
        }
        return null;
    }

    protected boolean executeCondition(Rule rule, Event event) {
        return (Boolean)MVEL.eval(rule.getCondition(), event.getVars());
    }

    protected Object executeAction(Rule rule, Event event) {
        return MVEL.eval(rule.getAction(), event.getVars());
    }

}
