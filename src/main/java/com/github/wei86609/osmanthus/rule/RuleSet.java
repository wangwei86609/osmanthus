package com.github.wei86609.osmanthus.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ruleset")
public class RuleSet extends Rule {

    public static final String FIRST_MATCH = "0";

    public static final String BAD_MATCH = "1";

    @XStreamImplicit
    protected List<Rule> rules;

    private Boolean order;

    private String model = BAD_MATCH;

    private Map<String, Rule> rulesMap;

    @XStreamAsAttribute
    private Integer reviewScore;

    @XStreamAsAttribute
    private Integer rejectScore;

    public Boolean getOrder() {
        return this.order;
    }

    public void setOrder(Boolean order) {
        this.order = order;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Rule> getRules() {
        if (this.rules == null) {
            this.rules = new ArrayList<Rule>();
        }
        return this.rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public void addRule(Rule rule) {
        if (this.rules == null) {
            this.rules = new ArrayList<Rule>();
        }
        this.rules.add(rule);
    }

    @Override
    public RuleType getType() {
        return RuleType.SET;
    }

    public Rule getStartRule() {
        if (this.rules == null || this.rules.size() <= 0) {
            return null;
        }
        for (Rule rule : this.rules) {
            if (RuleType.START.equals(rule.getType())) {
                return rule;
            }
        }
        return this.rules.get(0);
    }

    public Map<String, Rule> getRulesMap() {
        return this.rulesMap;
    }

    public void setRulesMap(Map<String, Rule> rulesMap) {
        this.rulesMap = rulesMap;
    }

    public void init() {
        if (this.rules == null) {
            return;
        }
        if (this.rulesMap == null) {
            this.rulesMap = new HashMap<String, Rule>();
        }
        for (Rule rule : this.rules) {
            this.rulesMap.put(rule.getId(), rule);
        }
    }

    public Integer getReviewScore() {
        return this.reviewScore;
    }

    public void setReviewScore(Integer reviewScore) {
        this.reviewScore = reviewScore;
    }

    public Integer getRejectScore() {
        return this.rejectScore;
    }

    public void setRejectScore(Integer rejectScore) {
        this.rejectScore = rejectScore;
    }
}
