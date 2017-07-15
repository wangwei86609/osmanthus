package com.github.wei86609.osmanthus.rule.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.rule.Rule.TYPE;
import com.github.wei86609.osmanthus.rule.ruleset.RuleSet;

public class GeneralRuleSetHandler implements RuleSetHandler{

    public void handle(RuleSet ruleSet) {
        List<Rule> rules = removeInvalidRules(ruleSet.getRules());
        sortRulesByPriority(rules);
        setNextRule(ruleSet.getToRuleId(),rules);
        ruleSet.setRules(rules);
    }

    private void setNextRule(String toNodeId,List<Rule> rules){
        for(Rule rule:rules){
            rule.setToRuleId(toNodeId);
        }
    }

    private void sortRulesByPriority(List<Rule> rules) {
        Collections.sort(rules, new Comparator<Rule>(){
            public int compare(Rule r1, Rule r2) {
                if (r1.getPriority()<r2.getPriority()){
                    return -1;
                }else if(r1.getPriority()==r2.getPriority()){
                    return 0;
                }else{
                    return 1;
                }
            }
       });
    }

    private List<Rule> removeInvalidRules(List<Rule> ruleList) {
        List<Rule> rules=new ArrayList<Rule>();
        for(Rule r:ruleList){
            if(StringUtils.isBlank(r.getCondition()) || StringUtils.isBlank(r.getAction())
                    ||!r.isValid()){
                continue;
            }
            rules.add(r);
        }
        return rules;
    }

    public boolean accept(RuleSet ruleSet) {
        return TYPE.SET.equals(ruleSet.getType())||TYPE.CARD.equals(ruleSet.getType());
    }

}
