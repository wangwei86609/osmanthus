/*
 * Copyright (c) 2017 PaiPai Credit Data Services (Shanghai) Co., Ltd All Rights Reserved.
 */

package com.github.wei86609.osmanthus.rule;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * @author wangwei19
 */
@XStreamAlias("vocabulary")
public class Vocabulary {

    @XStreamOmitField
    private String id;
    @XStreamAsAttribute
    private String name;
    @XStreamAsAttribute
    private String dataType;
    @XStreamAsAttribute
    private String category;
    @XStreamAsAttribute
    private String defaultVal;
    @XStreamAsAttribute
    private boolean init;
    @XStreamAsAttribute
    private String refDataSourceId;
    @XStreamAsAttribute
    private String refDataSourceType;
    @XStreamAsAttribute
    private String parseStyle;
    @XStreamAsAttribute
    private String resMappingField;
    @XStreamAsAttribute
    private String value;
    @XStreamAsAttribute
    private String methodClass;
    @XStreamAsAttribute
    private String methodName;
    @XStreamAsAttribute
    private String methodArgs;
    @XStreamAsAttribute
    private boolean constant;
    @XStreamAsAttribute
    private boolean velocity;
    @XStreamAsAttribute
    private int length;
    @XStreamAsAttribute
    private String refListTableName;
    @XStreamAsAttribute
    private String refListId;

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isVelocity() {
        return this.velocity;
    }

    public void setVelocity(boolean velocity) {
        this.velocity = velocity;
    }

    public String getMethodClass() {
        return this.methodClass;
    }

    public void setMethodClass(String methodClass) {
        this.methodClass = methodClass;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodArgs() {
        return this.methodArgs;
    }

    public void setMethodArgs(String methodArgs) {
        this.methodArgs = methodArgs;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDefaultVal() {
        return this.defaultVal;
    }

    public void setDefaultVal(String defaultVal) {
        this.defaultVal = defaultVal;
    }

    public boolean isInit() {
        return this.init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public String getRefDataSourceId() {
        return this.refDataSourceId;
    }

    public void setRefDataSourceId(String refDataSourceId) {
        this.refDataSourceId = refDataSourceId;
    }

    public String getParseStyle() {
        return this.parseStyle;
    }

    public void setParseStyle(String parseStyle) {
        this.parseStyle = parseStyle;
    }

    public String getResMappingField() {
        return this.resMappingField;
    }

    public void setResMappingField(String resMappingField) {
        this.resMappingField = resMappingField;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefDataSourceType() {
        return this.refDataSourceType;
    }

    public void setRefDataSourceType(String refDataSourceType) {
        this.refDataSourceType = refDataSourceType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Vocabulary other = (Vocabulary) obj;
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    public boolean isConstant() {
        return this.constant;
    }

    public void setConstant(boolean constant) {
        this.constant = constant;
    }

    public String getRefListTableName() {
        return this.refListTableName;
    }

    public void setRefListTableName(String refListTableName) {
        this.refListTableName = refListTableName;
    }

    public String getRefListId() {
        return this.refListId;
    }

    public void setRefListId(String refListId) {
        this.refListId = refListId;
    }

}
