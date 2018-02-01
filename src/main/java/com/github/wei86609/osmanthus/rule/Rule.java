package com.github.wei86609.osmanthus.rule;

import com.github.wei86609.osmanthus.ds.DataSource;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.List;
import java.util.Set;

@XStreamAlias("rule")
public class Rule {
    public enum RuleType {
        START, RULE, SPLIT, SET, CARD, PARALLEL, MERGE, END, STATISTICS_COUNT, HTTP, METHOD
    }

    @XStreamOmitField
    private RuleType type;

    @XStreamAsAttribute
    private String id;

    @XStreamAsAttribute
    private String name;

    @XStreamAsAttribute
    private String code;

    @XStreamAsAttribute
    private String fromRuleId;

    @XStreamAsAttribute
    private String toRuleId;

    @XStreamOmitField
    private Integer timeout;

    @XStreamOmitField
    private Integer priority;

    @XStreamOmitField
    private Integer multipleTimes = 0;

    @XStreamOmitField
    private Boolean valid = true;

    private Boolean isPilotRun;

    private String matchedPattern;

    @XStreamOmitField
    private Double riskScore;

    @XStreamOmitField
    private Double ruleScore;

    @XStreamOmitField
    private Long startTime;

    @XStreamOmitField
    private Long endTime;

    @XStreamOmitField
    private Boolean error;

    @XStreamOmitField
    private String errorMsg;

    @XStreamOmitField
    private String errorCode;

    @XStreamOmitField
    private Boolean isHit;

    private List<Vocabulary> vocabularies;

    private List<Condition> conditions;

    private List<Action> actions;

    private List<Kpi> kpis;

    private Set<DataSource> dataSources;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFromRuleId() {
        return this.fromRuleId;
    }

    public void setFromRuleId(String fromRuleId) {
        this.fromRuleId = fromRuleId;
    }

    public String getToRuleId() {
        return this.toRuleId;
    }

    public void setToRuleId(String toRuleId) {
        this.toRuleId = toRuleId;
    }

    public Integer getTimeout() {
        return this.timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getMultipleTimes() {
        return this.multipleTimes;
    }

    public void setMultipleTimes(Integer multipleTimes) {
        this.multipleTimes = multipleTimes;
    }

    public Boolean getValid() {
        return this.valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Boolean getIsPilotRun() {
        return this.isPilotRun;
    }

    public void setIsPilotRun(Boolean isPilotRun) {
        this.isPilotRun = isPilotRun;
    }

    public String getMatchedPattern() {
        return this.matchedPattern;
    }

    public void setMatchedPattern(String matchedPattern) {
        this.matchedPattern = matchedPattern;
    }

    public Long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Boolean getError() {
        return this.error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Boolean getIsHit() {
        return this.isHit;
    }

    public void setIsHit(Boolean isHit) {
        this.isHit = isHit;
    }

    public List<Vocabulary> getVocabularies() {
        return this.vocabularies;
    }

    public void setVocabularies(List<Vocabulary> vocabularies) {
        this.vocabularies = vocabularies;
    }

    public List<Condition> getConditions() {
        return this.conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public List<Action> getActions() {
        return this.actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public List<Kpi> getKpis() {
        return this.kpis;
    }

    public void setKpis(List<Kpi> kpis) {
        this.kpis = kpis;
    }

    public RuleType getType() {
        return RuleType.RULE;
    }

    public Double getRiskScore() {
        return this.riskScore;
    }

    public void setRiskScore(Double riskScore) {
        this.riskScore = riskScore;
    }

    public Double getRuleScore() {
        return this.ruleScore;
    }

    public void setRuleScore(Double ruleScore) {
        this.ruleScore = ruleScore;
    }

    public Set<DataSource> getDataSources() {
        return this.dataSources;
    }

    public void setDataSources(Set<DataSource> dataSources) {
        this.dataSources = dataSources;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
