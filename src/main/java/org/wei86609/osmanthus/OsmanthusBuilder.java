package org.wei86609.osmanthus;

import org.wei86609.osmanthus.node.executor.EmptyNodeExecutor;
import org.wei86609.osmanthus.node.executor.RuleExecutor;
import org.wei86609.osmanthus.node.executor.SplitRuleExecutor;
import org.wei86609.osmanthus.node.executor.ruleset.CardRuleSetExecutor;
import org.wei86609.osmanthus.node.executor.ruleset.GeneralRuleSetExecutor;
import org.wei86609.osmanthus.node.handler.GeneralRuleSetHandler;
import org.wei86609.osmanthus.translator.FileRuleSetTranslator;
import org.wei86609.osmanthus.translator.FlowFileTranslator;

public abstract class OsmanthusBuilder {

    public static Osmanthus build(){
        Osmanthus osmanthus=new Osmanthus();
        osmanthus.setFlowFileTranslator(new FlowFileTranslator());
        osmanthus.setRuleSetTranslator(new FileRuleSetTranslator());
        //add RuleExceutors
        RuleExecutor ruleExecutor=new RuleExecutor();
        osmanthus.addNodeExecutor(new SplitRuleExecutor());
        osmanthus.addNodeExecutor(new EmptyNodeExecutor());
        osmanthus.addNodeExecutor(ruleExecutor);
        //add RuleSetExcecutors
        GeneralRuleSetHandler generalRuleSetHandler=new GeneralRuleSetHandler();
        GeneralRuleSetExecutor generalRuleSetExecutor=new GeneralRuleSetExecutor();
        generalRuleSetExecutor.setRuleExecutor(ruleExecutor);
        generalRuleSetExecutor.addRuleSetHandler(generalRuleSetHandler);
        osmanthus.addNodeExecutor(generalRuleSetExecutor);
        CardRuleSetExecutor cardRuleSetExecutor=new CardRuleSetExecutor();
        cardRuleSetExecutor.setRuleExecutor(ruleExecutor);
        cardRuleSetExecutor.addRuleSetHandler(generalRuleSetHandler);
        osmanthus.addNodeExecutor(cardRuleSetExecutor);
        return osmanthus;
    }
}
