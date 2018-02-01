/*
 * Copyright (c) 2017 PaiPai Credit Data Services (Shanghai) Co., Ltd All Rights Reserved.
 */

package com.github.wei86609.osmanthus.el;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.rule.Action;
import com.github.wei86609.osmanthus.rule.Expression;

import java.util.ArrayList;
import java.util.List;


/**
 * @author wangwei19
 */
public class ElBuilder {

    private static volatile ElBuilder elBuilder;

    private List<EL> elList = new ArrayList<EL>();

    private ElBuilder() {
        this.elList.add(new MvelEL());
    }

    public void addEL(EL el) {
        if (el != null) {
            this.elList.add(el);
        }
    }

    public static ElBuilder build() {
        if (elBuilder == null) {
            synchronized (ElBuilder.class) {
                if (elBuilder == null) {
                    elBuilder = new ElBuilder();
                }
            }
        }
        return elBuilder;
    }

    public boolean executeCondition(Expression exp, Event event) {
        for (EL el : this.elList) {
            if (el.isAcceptable(exp.getLeftVoc() == null ? null : exp
                    .getLeftVoc().getDataType(), exp.getOp())) {
                return el.executeCondition(exp, event);
            }
        }
        return false;
    }

    public void executeAction(Action action, Event event) {
        for (EL el : this.elList) {
            if (el.isAcceptable(null, action.getExp().getOp())) {
                el.executeAction(action, event);
            }
        }
    }

    public Object executeStrExp(String strExp, Event event) {
        for (EL el : this.elList) {
            if (el.isAcceptable(null, null)) {
                return el.executeStrExp(strExp, event);
            }
        }
        return false;
    }
}
