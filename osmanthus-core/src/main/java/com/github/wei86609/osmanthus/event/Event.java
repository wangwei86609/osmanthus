package com.github.wei86609.osmanthus.event;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class Event {

    public static final String RULE_SCORE = "ruleScore";

    public static final String WEIGHT_SCORE_SUFFIX = "_weightScore";

    public static final String WEIGHT_SCORE = "weightScore";

    public Event() {
        this.variables.put(WEIGHT_SCORE, 0);
    }

    private final Map<String, Object> variables = new HashMap<String, Object>();

    private final Set<String> convertVars = new HashSet<String>();

    private final List<Event> subEvents = new ArrayList<Event>();

    private String id;

    private String appKey;

    private String appSecret;

    private String strategyId;

    private String version;

    private String strategyName;

    private String isMock;

    private String isTest;

    private long startTime;

    private long endTime;

    private boolean error;

    private String currentRuleId;

    private String dateFormat = "yyyy-MM-dd HH:mm:ss";

    private String userId;

    public String getIsTest() {
        return this.isTest;
    }

    public void setIsTest(String isTest) {
        this.isTest = isTest;
    }

    public String getDateFormat() {
        return this.dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Event> getSubEvents() {
        return this.subEvents;
    }

    public void addSubEvent(Event subEvent) {
        this.subEvents.add(subEvent);
    }

    public boolean isError() {
        return this.error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addVar(String key, Object value) {
        if (!StringUtils.isEmpty(key)) {
            this.variables.put(key, value);
        }
    }

    public Object getVar(String key) {
        if (!StringUtils.isEmpty(key)) {
            return this.variables.get(key);
        }
        return null;
    }

    public String getIsMock() {
        return this.isMock;
    }

    public void setIsMock(String isMock) {
        this.isMock = isMock;
    }

    public Map<String, Object> getVariables() {
        return this.variables;
    }

    public String getCurrentRuleId() {
        return this.currentRuleId;
    }

    public void setCurrentRuleId(String currentRuleId) {
        this.currentRuleId = currentRuleId;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getStrategyId() {
        return this.strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    public String getStrategyName() {
        return this.strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppSecret() {
        return this.appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public Event copy() {
        Event event = new Event();
        event.setId(this.getId());
        event.setStrategyId(this.strategyId);
        event.setCurrentRuleId(this.getCurrentRuleId());
        event.setStartTime(this.startTime);
        event.setEndTime(this.endTime);
        event.setStrategyName(this.strategyName);
        event.setAppKey(this.appKey);
        event.setAppSecret(this.appSecret);
        event.setUserId(this.userId);
        event.setVersion(this.version);
        event.setIsMock(this.isMock);
        event.setIsTest(this.isTest);
        event.setDateFormat(this.dateFormat);
        event.getVariables().putAll(this.getVariables());
        return event;
    }

    public Set<String> getConvertVars() {
        return this.convertVars;
    }
}
