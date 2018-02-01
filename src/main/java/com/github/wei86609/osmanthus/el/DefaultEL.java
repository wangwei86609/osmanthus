/*
 * Copyright (c) 2018 PaiPai Credit Data Services (Shanghai) Co., Ltd All Rights Reserved.
 */

package com.github.wei86609.osmanthus.el;

import com.github.wei86609.osmanthus.enums.DataTypeEnum;
import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.exception.RuleException;
import com.github.wei86609.osmanthus.rule.Action;
import com.github.wei86609.osmanthus.rule.Expression;
import com.github.wei86609.osmanthus.rule.Vocabulary;
import com.github.wei86609.osmanthus.utils.IDUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;

/**
 * @author wangwei19
 */
public abstract class DefaultEL implements EL {

    private static final Logger logger = LoggerFactory
            .getLogger(DefaultEL.class);


    public abstract boolean executeCondition(Expression exp, Event event);


    public abstract void executeAction(Action action, Event event);


    public abstract Object executeStrExp(String strExp, Event event);

    @Override
    public abstract boolean isAcceptable(String varType, String op);

    public Object getVocNameOrVal(Event event, Vocabulary voc, String varType,
                                  boolean isVal) {
        if (voc.isConstant()) {
            String tempVarName = IDUtils.getId("VAR");
            Object tempVarVal = StringUtils.EMPTY;
            if (DataTypeEnum.STRING.getType().equalsIgnoreCase(varType)) {
                tempVarVal = String.valueOf(voc.getValue());
            } else if (DataTypeEnum.INTEGER.getType().equalsIgnoreCase(varType)) {
                try {
                    tempVarVal = NumberUtils.toInt(voc.getValue());
                } catch (Exception e) {
                    logger.error("{}, parse {} to integer occurs error ",
                            event.getId(), voc.getValue(), e);
                    throw new RuleException(
                            RuleException.INVALID_VARIABLE_TYPE, String.format(
                                    RuleException.INVALID_VAR_FORMAT,
                                    voc.getValue()));
                }
            } else if (DataTypeEnum.DOUBLE.getType().equalsIgnoreCase(varType)) {
                try {
                    tempVarVal = NumberUtils.toDouble(voc.getValue());
                } catch (Exception e) {
                    logger.error("{}, parse {} to double occurs error ",
                            event.getId(), voc.getValue(), e);
                    throw new RuleException(
                            RuleException.INVALID_VARIABLE_TYPE, String.format(
                                    RuleException.INVALID_VAR_FORMAT,
                                    voc.getValue()));
                }
            } else if (DataTypeEnum.DATE.getType().equalsIgnoreCase(varType)) {
                try {
                    tempVarVal = DateUtils.parseDate(voc.getValue(),
                            event.getDateFormat());
                } catch (ParseException e) {
                    logger.error("{}, parse {} to date occurs error ",
                            event.getId(), voc.getValue(), e);
                    throw new RuleException(
                            RuleException.INVALID_VARIABLE_TYPE, String.format(
                                    RuleException.INVALID_VAR_FORMAT,
                                    voc.getValue()));
                }
            } else {
                tempVarVal = voc.getValue();
            }
            event.addVar(tempVarName, tempVarVal);
            logger.info("{}, Create a temporary variable [{}] value =[{}]",
                    event.getId(), tempVarName, voc.getValue());
            return tempVarName;
        } else {
            return isVal ? event.getVar(voc.getName()) : voc.getName();
        }
    }

}
