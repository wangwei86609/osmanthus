package com.github.wei86609.osmanthus.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("flow")
public class Flow extends Rule{

    private Map<String,Rule> ruleMap;

    @XStreamAsAttribute
    private String model;

    @XStreamImplicit
    private List<Rule> rules;

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public Map<String, Rule> getRuleMap() {
        return ruleMap;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Rule getStartRule(){
        for(Rule rule:rules){
            if(TYPE.START.equals(rule.getType())){
                return rule;
            }
        }
        return null;
    }

    public void init(){
        if(ruleMap==null){
            ruleMap=new HashMap<String,Rule>();
        }
        for(Rule rule:rules){
            ruleMap.put(rule.getId(), rule);
        }
    }

}
