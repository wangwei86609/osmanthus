package com.github.wei86609.osmanthus;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.github.wei86609.osmanthus.rule.Flow;
import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.translator.RuleTranslator;
import com.github.wei86609.osmanthus.translator.XmlFileRuleTranslator;

public class ConfigurationBuilder {

    private final static Logger logger = Logger.getLogger(ConfigurationBuilder.class);

    private RuleTranslator ruleTranslator;

    private final ConcurrentHashMap<String, Flow> flowMaps = new ConcurrentHashMap<String, Flow>();

    private final ConcurrentHashMap<String, Rule> ruleMaps = new ConcurrentHashMap<String, Rule>();

    private volatile static ConfigurationBuilder builder;

    private ConfigurationBuilder(){
        this.ruleTranslator=new XmlFileRuleTranslator();
    }

    public RuleTranslator getRuleTranslator() {
        return ruleTranslator;
    }

    public void setRuleTranslator(RuleTranslator ruleTranslator) {
        this.ruleTranslator = ruleTranslator;
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
        Map<String,Flow> flows=ruleTranslator.getFlows();
        if(flows==null ||flows.isEmpty()){
            return builder;
        }
        flowMaps.putAll(flows);
        Collection<Flow> fvalues=flowMaps.values();
        for(Flow flow:fvalues){
            if(flow.getRules()==null ||flow.getRules().isEmpty()){
                continue;
            }
            Map<String,Rule> externalRules =ruleTranslator.getExternalRules();
            if(externalRules==null ||externalRules.isEmpty()){
                break;
            }
            ruleMaps.putAll(externalRules);
            logger.debug("Flow ["+flow.getId()+"] will meger its rules with external rules");
            mergeRulesFromExternal(flow,externalRules);
        }
        return builder;
    }

    public Rule getExternalRuleById(String ruleId) throws Exception{
        return ruleMaps.get(ruleId);
    }

    public Flow getFlowById(String flowId) throws Exception{
        if(!flowMaps.containsKey(flowId)){
            Flow flow= ruleTranslator.getFlowById(flowId);
            if(flow!=null){
                flowMaps.put(flow.getId(), flow);
            }
        }
        return flowMaps.get(flowId);
    }

    public Rule getFirstRuleByFlow(String flowId) throws Exception{
        Flow flow=getFlowById(flowId);
        if(flow!=null){
            return flow.getRules().get(0);
        }
        return null;
    }

    public Rule getRuleByFlow(String flowId,String ruleId) throws Exception{
        Flow flow=getFlowById(flowId);
        if(flow!=null){
            return flow.getRuleMap().get(ruleId);
        }
        return null;
    }

    private void mergeRulesFromExternal(Flow flow,Map<String,Rule> externalRules){
        Map<String,Rule> flowMapNodes=flow.getRuleMap();
        Iterator<String> ite = flowMapNodes.keySet().iterator();
        while(ite.hasNext()){
            String key=ite.next();
            Rule frule=flowMapNodes.get(key);
            if(frule.isExternal()){
                Rule rule=externalRules.get(key);
                if(rule!=null){
                    rule.setFromNodeId(frule.getFromNodeId());
                    rule.setToNodeId(frule.getToNodeId());
                    flowMapNodes.put(key, rule);
                }
            }
        }
    }

    public void destroy(){
        flowMaps.clear();
        ruleMaps.clear();
    }

}
