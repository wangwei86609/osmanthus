package com.github.wei86609.osmanthus.translator;

import com.github.wei86609.osmanthus.rule.*;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public abstract class RuleTranslator{

    protected Flow xml2Flow(File xmlfile) throws FileNotFoundException{
        XStream xs = new XStream();
        xs.processAnnotations(new Class[]{Flow.class,Start.class,End.class,RuleSet.class,Rule.class,Split.class,Constraint.class,Parallel.class,Merge.class});
        return (Flow)xs.fromXML(new FileInputStream(xmlfile));
    }

    protected Flow xml2Flow(String content) throws FileNotFoundException{
        XStream xs = new XStream();
        xs.processAnnotations(new Class[]{Flow.class,Start.class,End.class,RuleSet.class,Rule.class,Split.class,Constraint.class,Parallel.class,Merge.class});
        return (Flow)xs.fromXML(content);
    }

    protected Rule xml2Rule(File file) throws FileNotFoundException{
        XStream xs = new XStream();
        xs.processAnnotations(new Class[]{Start.class,End.class,RuleSet.class,Rule.class,Split.class,Constraint.class,Parallel.class,Merge.class});
        return (Rule)xs.fromXML(new FileInputStream(file));
    }

    public abstract Map<String,Flow> getFlows()throws Exception;

    public abstract Flow getFlowById(String flowId)throws Exception;

    public abstract Rule getExternalRuleById(String nodeId)throws Exception;

    public abstract Map<String,Rule> getExternalRules()throws Exception;

}
