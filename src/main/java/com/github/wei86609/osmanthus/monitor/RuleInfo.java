package com.github.wei86609.osmanthus.monitor;

public class RuleInfo {
    
    private String eventId;
    
    private String flowId;
    
    private String ruleId;
    
    private String condition;
    
    private String action;
    
    private boolean resultOfConditoin;
    
    private Object resultOfAction;
    
    private long costTime;
    
    private boolean error;

    private String errorMsg;
    
    private String fromNodeId;
    
    private String toNodeId;
    
    private String ruleType;
    
    private String eventModel;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isResultOfConditoin() {
        return resultOfConditoin;
    }

    public void setResultOfConditoin(boolean resultOfConditoin) {
        this.resultOfConditoin = resultOfConditoin;
    }

    public Object getResultOfAction() {
        return resultOfAction;
    }

    public void setResultOfAction(Object resultOfAction) {
        this.resultOfAction = resultOfAction;
    }

    public long getCostTime() {
        return costTime;
    }

    public void setCostTime(long costTime) {
        this.costTime = costTime;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getFromNodeId() {
        return fromNodeId;
    }

    public void setFromNodeId(String fromNodeId) {
        this.fromNodeId = fromNodeId;
    }

    public String getToNodeId() {
        return toNodeId;
    }

    public void setToNodeId(String toNodeId) {
        this.toNodeId = toNodeId;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getEventModel() {
        return eventModel;
    }

    public void setEventModel(String eventModel) {
        this.eventModel = eventModel;
    }

    @Override
    public String toString() {
        return "Supervisor [eventId=" + eventId + ", flowId=" + flowId + ", ruleId=" + ruleId + ", condition="
                + condition + ", action=" + action + ", resultOfConditoin=" + resultOfConditoin + ", resultOfAction="
                + resultOfAction + ", costTime=" + costTime + ", error=" + error + ", errorMsg=" + errorMsg
                + ", fromNodeId=" + fromNodeId + ", toNodeId=" + toNodeId + ", ruleType=" + ruleType + ", eventModel="
                + eventModel + "]";
    }

}
