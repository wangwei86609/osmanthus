package org.wei86609.osmanthus.node.ruleset;

import java.util.ArrayList;
import java.util.List;

import org.wei86609.osmanthus.node.Node;
import org.wei86609.osmanthus.node.Rule;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("general")
public class GeneralRuleSet extends Node{

    public final static String FIRST_MODEL="first-match";

    public final static String LAST_MODEL="last-match";

    public final static String WEIGHT_MODEL="weight";

    @XStreamAsAttribute
    private String model;

    @XStreamImplicit
    private List<Rule> rules;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

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
