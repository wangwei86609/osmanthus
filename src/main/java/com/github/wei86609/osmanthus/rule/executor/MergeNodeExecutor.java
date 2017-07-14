package com.github.wei86609.osmanthus.rule.executor;

import org.apache.log4j.Logger;
import org.mvel2.MVEL;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.monitor.RuleInfo;
import com.github.wei86609.osmanthus.rule.Merge;
import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.rule.Rule.TYPE;

public class MergeNodeExecutor extends CommonExecutor{

    private final static Logger logger = Logger.getLogger(MergeNodeExecutor.class);

    @Override
    public String run(Event event, Rule rule,RuleInfo ruleInfo) throws Exception {
        Merge merge=(Merge)rule;
        logger.debug("Merge["+merge.getId()+"] of the event {"+event+"} has ["+merge.getLineCnt()+"] threads need to merge");
        if(merge.canMerge(event)){
            logger.debug("Merge["+merge.getId()+"] of the event {"+event+"} will merge all threads");
            return merge.getToNodeId();
        }
        return null;
    }

    protected boolean executeCondition(String condition, Event context) {
        return (Boolean)MVEL.eval(condition, context.getVars());
    }

    @Override
    public TYPE getType() {
        return TYPE.MERGE;
    }

}
