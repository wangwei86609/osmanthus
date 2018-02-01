package com.github.wei86609.osmanthus.event;

import com.github.wei86609.osmanthus.enums.DataTypeEnum;
import com.github.wei86609.osmanthus.exception.RuleException;
import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.rule.Vocabulary;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

public class DefaultEngineIntercepter implements EngineIntercepter {

    protected void convertVal(Vocabulary voc, Event event) throws RuleException {
        String value = voc.getValue();
        if (value == null) {
            value = voc.getDefaultVal();
        }
        if (StringUtils.isEmpty(value)) {
            throw new RuleException(
                    RuleException.VARIABLE_IS_EMPTY,
                    String.format(RuleException.VAR_EMPTY_FORMAT, voc.getName()));
        }
        Object cVal = null;
        if (DataTypeEnum.INTEGER.getType().equalsIgnoreCase(voc.getDataType())) {
            try {
                cVal = Integer.parseInt(value);
            } catch (Exception e) {
                throw new RuleException(RuleException.INVALID_VARIABLE_TYPE,
                        String.format(RuleException.INVALID_VAR_FORMAT,
                                voc.getName()));
            }
        } else if (DataTypeEnum.DOUBLE.getType().equalsIgnoreCase(
                voc.getDataType())) {
            try {
                cVal = Double.parseDouble(value);
            } catch (Exception e) {
                throw new RuleException(RuleException.INVALID_VARIABLE_TYPE,
                        String.format(RuleException.INVALID_VAR_FORMAT,
                                voc.getName()));
            }
        } else if (DataTypeEnum.DATE.getType().equalsIgnoreCase(
                voc.getDataType())) {
            try {
                cVal = DateUtils.parseDate(value, event.getDateFormat());
            } catch (Exception e) {
                throw new RuleException(RuleException.INVALID_VARIABLE_TYPE,
                        String.format(RuleException.INVALID_VAR_FORMAT,
                                voc.getName()));
            }
        } else if (DataTypeEnum.BOOLEAN.getType().equalsIgnoreCase(
                voc.getDataType())) {
            try {
                cVal = Boolean.parseBoolean(value);
            } catch (Exception e) {
                throw new RuleException(RuleException.INVALID_VARIABLE_TYPE,
                        String.format(RuleException.INVALID_VAR_FORMAT,
                                voc.getName()));
            }
        } else if (DataTypeEnum.STRING.getType().equalsIgnoreCase(
                voc.getDataType())) {
            if (value != null && voc.getLength() > 0
                    && value.length() > voc.getLength()) {
                throw new RuleException(RuleException.EXCEED_VARIABLE_LENGTH,
                        String.format(RuleException.INVALID_VAR_FORMAT,
                                voc.getName()));
            }
            cVal = value;
        } else {
            throw new RuleException(RuleException.INVALID_VARIABLE_TYPE,
                    String.format(RuleException.INVALID_VAR_FORMAT,
                            voc.getName()));
        }
        event.addVar(voc.getName(), cVal);
        event.getConvertVars().add(voc.getName());
    }

    @Override
    public void init(Event event) {

    }

    @Override
    public void complete(Event event) {
    }

    @Override
    public void exception(Event event, Exception e) {

    }

    @Override
    public void beforeRule(Event event, Rule rule) throws RuleException {

    }

    @Override
    public void afterRule(Event event, Rule rule) throws RuleException {

    }

    @Override
    public void hasErrorRule(Event event, Rule rule, Exception e) {

    }

    @Override
    public void initRuleSet(Event event, Rule ruleSet) throws RuleException {
    }

}
