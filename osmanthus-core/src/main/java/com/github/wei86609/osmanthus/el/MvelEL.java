package com.github.wei86609.osmanthus.el;


import com.github.wei86609.osmanthus.enums.OperatorEnum;
import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.rule.Action;
import com.github.wei86609.osmanthus.rule.Expression;
import org.apache.commons.lang3.StringUtils;
import org.mvel2.MVEL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MvelEL extends DefaultEL {

    private static final Logger logger = LoggerFactory.getLogger(MvelEL.class);

    public MvelEL() {
        // OptimizerFactory.setDefaultOptimizer(OptimizerFactory.SAFE_REFLECTIVE);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.ppc.gypsophila.engine.core.el.EL#executeCondition(com.ppc.gypsophila
     * .engine.core.rule.Expression, java.util.Map)
     */
    @Override
    public boolean executeCondition(Expression exp, Event event) {
        if (exp.getLeftVoc() == null || exp.getRightVoc() == null) {
            return false;
        }
        if (exp.getLeftVoc() == null && exp.getRightVoc() == null) {
            return true;
        }
        StringBuilder expBuf = new StringBuilder();
        expBuf.append(getVocNameOrVal(event, exp.getLeftVoc(),
                StringUtils.EMPTY, false));
        expBuf.append(StringUtils.SPACE);
        expBuf.append(StringUtils.trimToEmpty(exp.getOp()));
        expBuf.append(StringUtils.SPACE);
        expBuf.append(getVocNameOrVal(event, exp.getRightVoc(), exp
                .getLeftVoc().getDataType(), false));
        Boolean b = (Boolean) executeStrExp(expBuf.toString(), event);
        return b;
    }

    @Override
    public Object executeStrExp(String strExp, Event event) {
        Object obj = MVEL.eval(strExp, event.getVariables());
        logger.info("{}, expression[{}], its result is [{}]", event.getId(),
                strExp, obj);
        return obj;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.ppc.gypsophila.engine.core.el.EL#executeAction(com.ppc.gypsophila
     * .engine.core.rule.Expression, java.util.Map)
     */
    @Override
    public void executeAction(Action action, Event event) {
        StringBuilder actionBuffer = new StringBuilder();
        if ("text".equals(action.getType())) {
            actionBuffer.append(action.getReturnVoc().getName())
                    .append(StringUtils.SPACE).append("=")
                    .append(StringUtils.SPACE)
                    .append(action.getExp().getLeftVoc().getName()).append(";");
        } else {
            actionBuffer
                    .append(getVocNameOrVal(event, action.getReturnVoc(),
                            StringUtils.EMPTY, false))
                    .append(StringUtils.SPACE)
                    .append("=")
                    .append(StringUtils.SPACE)
                    .append("(")
                    .append(getVocNameOrVal(event,
                            action.getExp().getLeftVoc(), StringUtils.EMPTY,
                            false))
                    .append(StringUtils.SPACE)
                    .append(action.getExp().getOp())
                    .append(StringUtils.SPACE)
                    .append(getVocNameOrVal(event, action.getExp()
                            .getRightVoc(), action.getExp().getLeftVoc()
                            .getDataType(), false)).append(")").append(";");
        }
        logger.info("{}, MevlEL execute action[{}]", event.getId(),
                actionBuffer.toString());
        MVEL.eval(actionBuffer.toString(), event.getVariables());
    }

    @Override
    public boolean isAcceptable(String varType, String op) {
        if (StringUtils.isEmpty(op) && StringUtils.isEmpty(varType)) {
            return true;
        }
        if (!OperatorEnum.CONTAINS.getOp().equalsIgnoreCase(op)
                && !OperatorEnum.NOT_CONTAINS.getOp().equalsIgnoreCase(op)
                && !OperatorEnum.IN.getOp().equalsIgnoreCase(op)
                && !OperatorEnum.NOT_IN.getOp().equalsIgnoreCase(op)) {
            logger.info("MevlEL execute op[{}]", op);
            return true;
        }
        return false;
    }

}
