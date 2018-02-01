package com.github.wei86609.osmanthus;

public enum InterceptorType {
    EVENT_INIT(0), EVENT_COMPLETE(1), RULE_BEFORE(2), RULE_POST(3), RULE_EXCEPTION(
            4), EVENT_EXCEPTION(5), RULESET_INIT(6);

    private int intValue;

    private InterceptorType(int intValue) {
        this.intValue = intValue;
    }

    public int getIntValue() {
        return this.intValue;
    }

}
