package org.wei86609.osmanthus.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("rule")
public class Rule extends Node{

    @XStreamAsAttribute
    private int priority;

    @XStreamAsAttribute
    private String title;

    @XStreamAsAttribute
    private boolean exclusive;

    @XStreamAsAttribute
    private int multipleTimes;

    @XStreamAsAttribute
    private boolean valid=true;

    private String condition;

    private String action;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Rule [condition=" + this.getCondition() + ", action=" + this.getAction() + ", type=" + getType() + ", id=" + getId()
                + ", priority=" + getPriority() + ", multipleTimes=" + getMultipleTimes() + ",exclusive=" + isExclusive()
                + "]";
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isExclusive() {
        return exclusive;
    }

    public void setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
    }

    public int getMultipleTimes() {
        return multipleTimes;
    }

    public void setMultipleTimes(int multipleTimes) {
        this.multipleTimes = multipleTimes;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public TYPE getType() {
        return TYPE.RULE;
    }

}
