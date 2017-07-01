package org.wei86609.osmanthus.node;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("node")
public class Node {

    public static enum TYPE {
        EMPTY,RULE,SPLIT,SET,CARD,PARALLEL
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

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

}
