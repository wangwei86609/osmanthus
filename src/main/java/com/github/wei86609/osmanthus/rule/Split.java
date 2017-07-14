package com.github.wei86609.osmanthus.rule;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("split")
public class Split extends Rule{

    @XStreamImplicit
    private List<Constraint> constraints;

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }

    @Override
    public TYPE getType() {
        return TYPE.SPLIT;
    }
}
