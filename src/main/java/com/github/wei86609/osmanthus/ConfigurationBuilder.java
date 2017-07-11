package com.github.wei86609.osmanthus;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.github.wei86609.osmanthus.node.Flow;
import com.github.wei86609.osmanthus.node.Node;
import com.github.wei86609.osmanthus.translator.NodeTranslator;
import com.github.wei86609.osmanthus.translator.XmlFileNodeTranslator;

public class ConfigurationBuilder {
    
    private final static Logger logger = Logger.getLogger(ConfigurationBuilder.class);
    
    private NodeTranslator nodeTranslator;

    private final ConcurrentHashMap<String, Flow> flowMaps = new ConcurrentHashMap<String, Flow>();
    
    private final ConcurrentHashMap<String, Node> nodeMaps = new ConcurrentHashMap<String, Node>();

    private volatile static ConfigurationBuilder builder;

    private ConfigurationBuilder(){
        this.nodeTranslator=new XmlFileNodeTranslator();
    }

    public NodeTranslator getNodeTranslator() {
        return nodeTranslator;
    }

    public void setNodeTranslator(NodeTranslator nodeTranslator) {
        this.nodeTranslator = nodeTranslator;
    }

    public static ConfigurationBuilder getBuilder(){
        if (builder == null) {
            synchronized (ConfigurationBuilder.class) {
                if (builder == null) {
                    builder = new ConfigurationBuilder();
                }
            }
        }
        return builder;
    }

    public ConfigurationBuilder loadConfiguration() throws Exception{
        flowMaps.clear();
        Map<String,Flow> flows=nodeTranslator.getFlows();
        if(flows==null ||flows.isEmpty()){
            return builder;
        }
        flowMaps.putAll(flows);
        Collection<Flow> fvalues=flowMaps.values();
        for(Flow flow:fvalues){
            if(flow.getNodes()==null ||flow.getNodes().isEmpty()){
                continue;
            }
            Map<String,Node> externalRules =nodeTranslator.getExternalNodes();
            if(externalRules==null ||externalRules.isEmpty()){
                break;
            }
            nodeMaps.putAll(externalRules);
            logger.debug("Flow ["+flow.getId()+"] will meger its rules with external rules");
            mergeRulesFromExternal(flow,externalRules);
        }
        return builder;
    }
    
    public Node getExternalNodeById(String nodeId) throws Exception{
        return nodeMaps.get(nodeId);
    }
    
    public Flow getFlowById(String flowId) throws Exception{
        if(!flowMaps.containsKey(flowId)){
            Flow flow= nodeTranslator.getFlowById(flowId);
            if(flow!=null){
                flowMaps.put(flow.getId(), flow);
            }
        }
        return flowMaps.get(flowId);
    }

    public Node getFirstNodeByFlow(String flowId) throws Exception{
        Flow flow=getFlowById(flowId);
        if(flow!=null){
            return flow.getNodes().get(0);
        }
        return null;
    }
    
    public Node getNodeByFlow(String flowId,String nodeId) throws Exception{
        Flow flow=getFlowById(flowId);
        if(flow!=null){
            return flow.getNodeMap().get(nodeId);
        }
        return null;
    }

    private void mergeRulesFromExternal(Flow flow,Map<String,Node> externalRules){
        Map<String,Node> flowMapNodes=flow.getNodeMap();
        Iterator<String> ite = flowMapNodes.keySet().iterator(); 
        while(ite.hasNext()){
            String key=ite.next();
            Node fNode=flowMapNodes.get(key);
            if(fNode.isExternal()){
                Node rule=externalRules.get(key);
                if(rule!=null){
                    rule.setFromNodeId(fNode.getFromNodeId());
                    rule.setToNodeId(fNode.getToNodeId());
                    flowMapNodes.put(key, rule);
                }
            }
        }
    }
    
    public void destroy(){
        flowMaps.clear();
        nodeMaps.clear();
    }

}
