package org.wei86609.osmanthus.node.handler;

import java.util.List;

import org.wei86609.osmanthus.Accepter;
import org.wei86609.osmanthus.node.Node;
import org.wei86609.osmanthus.node.Rule;

public interface RuleSetHandler extends Accepter<Node>{

    public List<Rule> handle(List<Rule> rules);

    public boolean accept(Node ruleSet);
}
