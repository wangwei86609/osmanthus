package org.wei86609.osmanthus.node.executor;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.wei86609.osmanthus.Executor;
import org.wei86609.osmanthus.event.Event;
import org.wei86609.osmanthus.event.Supervisor;
import org.wei86609.osmanthus.node.Node;
import org.wei86609.osmanthus.node.Node.TYPE;

public abstract class NodeExecutor implements Executor<Node,String>{

    private final static Logger logger = Logger.getLogger(NodeExecutor.class);
    
    public String execute(Event event,Node node){
        String nextNodeId=StringUtils.EMPTY;
        long startTime=System.currentTimeMillis();
        Supervisor supervisor=new Supervisor();
        try {
            supervisor.setEventId(event.getEventId());
            supervisor.setEventModel(event.getModel().name());
            supervisor.setFlowId(event.getFlowId());
            supervisor.setFromNodeId(node.getFromNodeId());
            supervisor.setRuleId(node.getId());
            supervisor.setRuleType(node.getType().name());
            //run node
            nextNodeId=run(event,node,supervisor);
            //enrich parameters
            supervisor.setToNodeId(nextNodeId);
            supervisor.setCostTime(System.currentTimeMillis()-startTime);
        } catch (Exception e) {
            event.setError(true);
            supervisor.setError(true);
            supervisor.setErrorMsg(e.getMessage());
            logger.error("Node["+node.getId()+"] occurs exception",e);
        }
        event.addSupervisor(supervisor);
        return nextNodeId;
    }
    
    public abstract String run(Event event, Node node,Supervisor supervisor) throws Exception;

    public abstract TYPE getType();

    public void stop(){
        
    }
}
