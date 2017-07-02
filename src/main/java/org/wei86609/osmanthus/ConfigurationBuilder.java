package org.wei86609.osmanthus;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.wei86609.osmanthus.node.Flow;
import org.wei86609.osmanthus.node.Node;
import org.wei86609.osmanthus.translator.FileRuleSetTranslator;
import org.wei86609.osmanthus.translator.FlowFileTranslator;

public class ConfigurationBuilder {

    private FileRuleSetTranslator ruleSetTranslator;

    private FlowFileTranslator flowFileTranslator;

    private final ConcurrentHashMap<String, Node> avaiableNodes = new ConcurrentHashMap<String, Node>();

    private volatile static ConfigurationBuilder builder;

    private ConfigurationBuilder() throws Exception {
        this.flowFileTranslator=new FlowFileTranslator();
        this.ruleSetTranslator=new FileRuleSetTranslator();
        loadConfiguration();
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

    public ConcurrentHashMap<String, Node> getAvaiableNodes() {
        return avaiableNodes;
    }

    public static ConfigurationBuilder getBuilder() throws Exception {
        if (builder == null) {
            synchronized (ConfigurationBuilder.class) {
                if (builder == null) {
                    builder = new ConfigurationBuilder();
                }
            }
        }
        return builder;
    }

    protected void loadConfiguration()throws Exception{
        List<Flow> flows=flowFileTranslator.getNodes();
        List<Node> rules =  ruleSetTranslator.getNodes();
        for(Flow flow:flows){
            combinRules(flow,rules);
        }
    }

    public Node loadOneFlow(String flowId) throws Exception{
        Flow flow=flowFileTranslator.getNode(flowId);
        List<Node> rules =  ruleSetTranslator.getNodes();
        combinRules(flow,rules);
        return flow.getNodes().get(0);
    }

    private void combinRules(Flow flow,List<Node> rules){
        if(flow==null || flow.getNodes()==null ||flow.getNodes().isEmpty()){
            return;
        }
        List<Node> flowNodes=flow.getNodes();
        for(Node fn:flowNodes){
            avaiableNodes.remove(fn.getId());
            if(fn.isExternal()){
                Node rule=getRuleById(rules,fn.getId());
                if(rule!=null){
                    rule.setFromNodeId(fn.getFromNodeId());
                    rule.setToNodeId(fn.getToNodeId());
                    avaiableNodes.put(fn.getId(), rule);
                }
            }else{
                avaiableNodes.put(fn.getId(), fn);
            }
        }
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
