package org.wei86609.osmanthus.node.executor;

import org.mvel2.MVEL;
import org.wei86609.osmanthus.event.Event;
import org.wei86609.osmanthus.node.Node;
import org.wei86609.osmanthus.node.Node.TYPE;
import org.wei86609.osmanthus.node.Rule;

public class RuleExecutor implements NodeExecutor {

    public TYPE getType() {
        return TYPE.RULE;
    }

    public Boolean execute(Event context,Node node) throws Exception{
        Rule rule=(Rule)node;
        boolean success=executeCondition(rule,context);
        if(success){
            executeAction(rule,context);
        }
        return success;
    }

    protected boolean executeCondition(Rule rule, Event context) {
        return (Boolean)MVEL.eval(rule.getCondition(), context.getVars());
    }

    protected void executeAction(Rule rule, Event context) {
        MVEL.eval(rule.getAction(), context.getVars());
    }

    public void stop() {

    }

}
