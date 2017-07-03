package org.wei86609.osmanthus.node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("flow")
public class Flow extends Node{

    private Map<String,Node> nodeMap=new HashMap<String,Node>();
    
    @XStreamAsAttribute
    private String model;

    @XStreamImplicit
    private List<Node> nodes;

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Map<String, Node> getNodeMap() {
        for(Node node:nodes){
            nodeMap.put(node.getId(), node);
        }
        return nodeMap;
    }

    public void setNodeMap(Map<String, Node> nodeMap) {
        this.nodeMap = nodeMap;
    }

}
