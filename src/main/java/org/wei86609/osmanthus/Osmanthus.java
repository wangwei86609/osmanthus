package org.wei86609.osmanthus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.wei86609.osmanthus.event.Event;
import org.wei86609.osmanthus.event.Event.MODEL;
import org.wei86609.osmanthus.node.Flow;
import org.wei86609.osmanthus.node.Node;
import org.wei86609.osmanthus.node.Node.TYPE;
import org.wei86609.osmanthus.node.executor.NodeExecutor;
import org.wei86609.osmanthus.translator.FileRuleSetTranslator;
import org.wei86609.osmanthus.translator.FlowFileTranslator;

public class Osmanthus{

    private final static Logger logger = Logger.getLogger(Osmanthus.class);

    private FileRuleSetTranslator ruleSetTranslator;

    private FlowFileTranslator flowFileTranslator;

    private Map<TYPE,NodeExecutor> nodeExecutorMap;

    private final static ThreadLocal<Map<String,Node>> avaiableNodes = new ThreadLocal<Map<String,Node>>();

    public void addNodeExecutor(List<NodeExecutor> nodeExecutors){
        if(nodeExecutorMap==null){
            nodeExecutorMap=new HashMap<TYPE,NodeExecutor>();
        }
        for(NodeExecutor nodeExecutor:nodeExecutors){
            nodeExecutorMap.put(nodeExecutor.getType(), nodeExecutor);
        }
    }

    public void addNodeExecutor(NodeExecutor nodeExecutor){
        if(nodeExecutorMap==null){
            nodeExecutorMap=new HashMap<TYPE,NodeExecutor>();
        }
        nodeExecutorMap.put(nodeExecutor.getType(), nodeExecutor);
    }

    public FileRuleSetTranslator getRuleSetTranslator() {
        return ruleSetTranslator;
    }

    public void setRuleSetTranslator(FileRuleSetTranslator ruleSetTranslator) {
        this.ruleSetTranslator = ruleSetTranslator;
    }

    public FlowFileTranslator getFlowFileTranslator() {
        return flowFileTranslator;
    }

    public void setFlowFileTranslator(FlowFileTranslator flowFileTranslator) {
        this.flowFileTranslator = flowFileTranslator;
    }

    public Boolean execute(Event event) throws Exception {
        logger.debug("Osmanthus begin to execute the event["+event.toString()+"]");
        Flow flow=flowFileTranslator.getNode(event.getFlowId());
        if(flow==null || flow.getNodes()==null ||flow.getNodes().isEmpty()){
            return false;
        }
        if(StringUtils.isNoneBlank(flow.getModel())){
            event.setModel(MODEL.valueOf(flow.getModel()));
        }
        List<Node> rules=  ruleSetTranslator.getNodes();
        List<Node> flowNodes=flow.getNodes();
        combinRulesAndFlowNodes(rules,flowNodes);
        runFlowNode(event,flowNodes.get(0).getId());
        return true;
    }

    private void combinRulesAndFlowNodes(List<Node> rules,List<Node> flowNodes) throws Exception{
        Map<String,Node> avaNodes=new HashMap<String,Node>();
        for(Node fn:flowNodes){
            if(fn.isExternal()){
                Node rule=getRuleById(rules,fn.getId());
                if(rule==null){
                    throw new Exception("External file dont find the rule ["+fn.getId()+"]");
                }
                rule.setFromNodeId(fn.getFromNodeId());
                rule.setToNodeId(fn.getToNodeId());
                avaNodes.put(fn.getId(), rule);
            }else{
                avaNodes.put(fn.getId(), fn);
            }
        }
        avaiableNodes.set(avaNodes);
    }

    private void runFlowNode(Event event,String nodeId)throws Exception{
        if(StringUtils.isBlank(nodeId)){
            return;
        }
        if(avaiableNodes.get()==null ||avaiableNodes.get().size()<=0){
            return;
        }
        Node node=avaiableNodes.get().get(nodeId);
        boolean succ= nodeExecutorMap.get(node.getType()).execute(event, node);
        if(!succ){
            logger.error("The node["+nodeId+"] of the flow["+event.getFlowId()+"] execute failure");
            throw new Exception("The node["+nodeId+"] of the flow["+event.getFlowId()+"] execute failure");
        }
        runFlowNode(event,node.getToNodeId());
    }

    private Node getRuleById(List<Node> rules,String id){
        for(Node rule:rules){
            if(rule.getId().equals(id)){
                return rule;
            }
        }
        return null;
    }

}
