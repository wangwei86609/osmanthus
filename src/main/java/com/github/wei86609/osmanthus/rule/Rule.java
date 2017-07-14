package com.github.wei86609.osmanthus.rule;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("rule")
public class Rule{
    public enum TYPE {
        START,RULE,SPLIT,SET,CARD,PARALLEL,MERGE,END
    }

    private TYPE type;

    @XStreamAsAttribute
    private String id;

    @XStreamAsAttribute
    private String name;

    @XStreamAsAttribute
    private String fromNodeId;

    @XStreamAsAttribute
    private String toNodeId;

    @XStreamAsAttribute
    private String description;

    @XStreamAsAttribute
    private int timeout;

    @XStreamAsAttribute
    private boolean external;

    public String getFromNodeId() {
        return fromNodeId;
    }

    public void setFromNodeId(String fromNodeId) {
        this.fromNodeId = fromNodeId;
    }

    public String getToNodeId() {
        return toNodeId;
    }

    public void setToNodeId(String toNodeId) {
        this.toNodeId = toNodeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isExternal() {
        return external;
    }

    public void setExternal(boolean external) {
        this.external = external;
    }


    public void setType(TYPE type) {
        this.type = type;
    }

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

    public TYPE getType() {
        return TYPE.RULE;
    }

    @Override
    public String toString() {
        return "Rule [type=" + type + ", id=" + id + ", name=" + name
                + ", fromNodeId=" + fromNodeId + ", toNodeId=" + toNodeId
                + ", description=" + description + ", timeout=" + timeout
                + ", external=" + external + ", priority=" + priority
                + ", title=" + title + ", exclusive=" + exclusive
                + ", multipleTimes=" + multipleTimes + ", valid=" + valid
                + ", condition=" + condition + ", action=" + action + "]";
    }
}
