package com.github.wei86609.osmanthus.rule;

import java.util.Set;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("knowledgePackage")
public class KnowledgePackage extends RuleSet {

    private Set<Vocabulary> inputVariables;

    public Set<Vocabulary> getInputVariables() {
        return this.inputVariables;
    }

    public void setInputVariables(Set<Vocabulary> inputVariables) {
        this.inputVariables = inputVariables;
    }

}
