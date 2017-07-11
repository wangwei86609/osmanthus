package com.github.wei86609.osmanthus.translator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import com.github.wei86609.osmanthus.node.Constraint;
import com.github.wei86609.osmanthus.node.End;
import com.github.wei86609.osmanthus.node.Flow;
import com.github.wei86609.osmanthus.node.Merge;
import com.github.wei86609.osmanthus.node.Node;
import com.github.wei86609.osmanthus.node.Parallel;
import com.github.wei86609.osmanthus.node.Rule;
import com.github.wei86609.osmanthus.node.Split;
import com.github.wei86609.osmanthus.node.Start;
import com.github.wei86609.osmanthus.node.ruleset.RuleSet;
import com.thoughtworks.xstream.XStream;

public abstract class NodeTranslator{
    
    protected Flow xml2Node(File xmlfile) throws FileNotFoundException{
        XStream xs = new XStream();
        xs.processAnnotations(new Class[]{Flow.class,Start.class,End.class,RuleSet.class,Rule.class,Split.class,Constraint.class,Parallel.class,Merge.class});
        return (Flow)xs.fromXML(new FileInputStream(xmlfile));
    }
    
    protected Flow xml2Node(String content) throws FileNotFoundException{
        XStream xs = new XStream();
        xs.processAnnotations(new Class[]{Flow.class,Start.class,End.class,RuleSet.class,Rule.class,Split.class,Constraint.class,Parallel.class,Merge.class});
        return (Flow)xs.fromXML(content);
    }
    
    protected Node xml2Rule(File file) throws FileNotFoundException{
        XStream xs = new XStream();
        xs.processAnnotations(new Class[]{Start.class,End.class,RuleSet.class,Rule.class,Split.class,Constraint.class,Parallel.class,Merge.class});
        return (Node)xs.fromXML(new FileInputStream(file));
    }
    
    public abstract Map<String,Flow> getFlows()throws Exception;

    public abstract Flow getFlowById(String flowId)throws Exception;

    public abstract Node getExternalNodeById(String nodeId)throws Exception;
    
    public abstract Map<String,Node> getExternalNodes()throws Exception;

}
