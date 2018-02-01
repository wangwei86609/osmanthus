package com.github.wei86609.osmanthus.el;


import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.rule.Action;
import com.github.wei86609.osmanthus.rule.Expression;

public interface EL {

    public boolean executeCondition(Expression exp, Event event);

    public void executeAction(Action action, Event event);

    public Object executeStrExp(String strExp, Event event);

    public boolean isAcceptable(String varType, String op);
}
