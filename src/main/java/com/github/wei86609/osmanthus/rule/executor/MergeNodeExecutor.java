package com.github.wei86609.osmanthus.rule.executor;

import org.apache.log4j.Logger;
import org.mvel2.MVEL;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.rule.Merge;
import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.rule.Rule.TYPE;

public class MergeNodeExecutor implements RuleExecutor{

    private final static Logger logger = Logger.getLogger(MergeNodeExecutor.class);

    public String execute(Event event, Rule rule) throws Exception {
        Merge merge=(Merge)rule;
        logger.debug("Merge["+merge.getId()+"] of the event {"+event+"} has ["+merge.getLineCnt()+"] threads need to merge");
        if(merge.canMerge(event)){
            logger.debug("Merge["+merge.getId()+"] of the event {"+event+"} will merge all threads");
            return merge.getToRuleId();
        }
        return null;
    }

    protected boolean executeCondition(String condition, Event context) {
        return (Boolean)MVEL.eval(condition, context.getVariables());
    }

    public TYPE getType() {
        return TYPE.MERGE;
    }

    public void stop() {

    }

}
