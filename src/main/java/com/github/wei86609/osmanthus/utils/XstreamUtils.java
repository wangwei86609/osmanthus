package com.github.wei86609.osmanthus.utils;


import com.github.wei86609.osmanthus.rule.*;
import com.github.wei86609.osmanthus.rule.ds.Interface;
import com.github.wei86609.osmanthus.rule.ds.arg.Arg;
import com.github.wei86609.osmanthus.rule.ds.arg.InArg;
import com.thoughtworks.xstream.XStream;

public abstract class XstreamUtils {

    protected static final Class[] SUPPORT_CLASSES = new Class[] {
            KnowledgePackage.class, Start.class, End.class, RuleSet.class,
            Rule.class, Split.class, Parallel.class, Merge.class,
            Vocabulary.class, Action.class, Condition.class, Expression.class,
            Interface.class, Arg.class, InArg.class };

    public static KnowledgePackage xml2Strategy(String content) {
        XStream xs = new XStream();
        xs.processAnnotations(SUPPORT_CLASSES);
        return (KnowledgePackage) xs.fromXML(content);
    }

    public static String strategy2XML(KnowledgePackage tactic) {
        XStream xs = new XStream();
        xs.processAnnotations(SUPPORT_CLASSES);
        return xs.toXML(tactic);
    }

}
