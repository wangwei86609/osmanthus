/*
 * Copyright (c) 2017 PaiPai Credit Data Services (Shanghai) Co., Ltd All Rights Reserved.
 */

package com.github.wei86609.osmanthus.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * @author wangwei19
 */
public class RuleException extends RuntimeException {

    public static final String INTERNAL_ERROR_CODE = "api.resp.sys#service_currently_unavailable";

    public static final String INVALID_VARIABLE_TYPE = "api.resp.sys#invalid_variable_type";

    public static final String INVALID_VARIABLE = "api.resp.sys#invalid_variable";

    public static final String EXCEED_VARIABLE_LENGTH = "api.resp.sys#exceed_variable_length";

    public static final String VARIABLE_IS_EMPTY = "api.resp.sys#variable_is_empty";

    public static final String INVALID_VAR_FORMAT = "变量 [%s]入参格式异常";

    public static final String INVALID_QUERY_VAR_FORMAT = "变量 [%s]查询异常";

    public static final String VAR_EMPTY_FORMAT = "变量 [%s]没有传入值";

    public static final String NO_DATASOURCE_FORMAT = "变量 [%s]没有绑定数据源,或者指定数据源不存在";

    public static final String UNKNOWN_EXCEPTION = "未知异常";

    private String code;

    private String msg;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RuleException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public RuleException(String msg) {
        super(msg);
        this.code = INTERNAL_ERROR_CODE;
        this.msg = msg;
    }

    public RuleException() {
        super(StringUtils.EMPTY);
        this.code = INTERNAL_ERROR_CODE;
        this.msg = StringUtils.EMPTY;

    }

}
