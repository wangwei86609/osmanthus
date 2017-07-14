package com.github.wei86609.osmanthus.translator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import com.github.wei86609.osmanthus.rule.Constraint;
import com.github.wei86609.osmanthus.rule.End;
import com.github.wei86609.osmanthus.rule.Flow;
import com.github.wei86609.osmanthus.rule.Merge;
import com.github.wei86609.osmanthus.rule.Parallel;
import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.rule.Split;
import com.github.wei86609.osmanthus.rule.Start;
import com.github.wei86609.osmanthus.rule.ruleset.RuleSet;
import com.thoughtworks.xstream.XStream;

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
