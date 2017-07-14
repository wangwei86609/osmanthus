package com.github.wei86609.osmanthus.rule.ruleset;

import java.util.ArrayList;
import java.util.List;

import com.github.wei86609.osmanthus.rule.Rule;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ruleset")
public class RuleSet extends Rule{

    @XStreamImplicit
    private List<Rule> rules;

    public List<Rule> getRules() {
        if(rules==null){
            rules = new ArrayList<Rule>();
        }
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public TYPE getType() {
        return TYPE.SET;
    }
}
