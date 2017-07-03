package org.wei86609.osmanthus.node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("flow")
public class Flow extends Node{
    
    private final Map<String,Node> mapNodes=new HashMap<String,Node>();

    @XStreamAsAttribute
    private String model;

    @XStreamImplicit
    private List<Node> nodes;

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
        for(Node node:nodes){
            mapNodes.put(node.getId(), node);
        }
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Map<String, Node> getMapNodes() {
        return mapNodes;
    }

}
