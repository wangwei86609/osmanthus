/*
 * Copyright (c) 2017 PaiPai Credit Data Services (Shanghai) Co., Ltd All Rights Reserved.
 */

package com.github.wei86609.osmanthus.enums;

/**
 * @author wangwei86609
 */
public enum OperatorEnum {

    ADD("+"), SUBTRACT("-"), MULTIPLY("*"), DIVIDE("/"), GT(">"), LT("<"), GT_EQ(
            ">="), LT_EQ("<="), EQ("=="), NEQ("!="), CONTAINS("contains"), NOT_CONTAINS(
            "not contains"), IN("in"), NOT_IN("not in"), ASSIGNMENT("=");

    private String op;

    /**
     * @param op
     */
    private OperatorEnum(String op) {
        this.op = op;
    }

    public String getOp() {
        return this.op;
    }

}
