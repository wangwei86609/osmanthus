package org.wei86609.osmanthus;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.wei86609.osmanthus.node.Flow;
import org.wei86609.osmanthus.node.Node;
import org.wei86609.osmanthus.translator.FileRuleSetTranslator;
import org.wei86609.osmanthus.translator.FlowFileTranslator;

public class ConfigurationBuilder {

    private FileRuleSetTranslator ruleSetTranslator;

    private FlowFileTranslator flowFileTranslator;

    private final ConcurrentHashMap<String, Flow> flowMaps = new ConcurrentHashMap<String, Flow>();

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

    public ConcurrentHashMap<String, Flow> getFlowMaps() {
        return flowMaps;
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

    public ConfigurationBuilder loadConfiguration()throws Exception{
        flowMaps.clear();
        Map<String,Flow> flows=flowFileTranslator.getNodes();
        flowMaps.putAll(flows);
        Map<String,Node> externalRules =  ruleSetTranslator.getNodes();
        Collection<Flow> fvalues=flowMaps.values();
        for(Flow flow:fvalues){
            if(flow.getNodes()==null ||flow.getNodes().isEmpty()){
                continue;
            }
            mergeRulesFromExternal(flow,externalRules);
        }
        
        return builder;
    }

    public Node getFirstNodeByFlow(String flowId) throws Exception{
        Flow flow=flowMaps.get(flowId);
        return flow.getNodes().get(0);
    }

    private void mergeRulesFromExternal(Flow flow,Map<String,Node> externalRules){
        Map<String,Node> flowMapNodes=flow.getMapNodes();
        for(Entry<String,Node> entry:flowMapNodes.entrySet()){
            if(entry.getValue().isExternal()){
                Node rule=externalRules.get(entry.getKey());
                if(rule!=null){
                    rule.setFromNodeId(entry.getValue().getFromNodeId());
                    rule.setToNodeId(entry.getValue().getToNodeId());
                    flow.getMapNodes().remove(entry.getKey());
                    flow.getMapNodes().put(entry.getKey(), rule);
                }
            }
        }
   }
 

}
