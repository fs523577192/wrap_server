package org.firas.common.response.input;

import java.io.Serializable;

public class JsonResponseInvalidDate extends JsonResponseInvalidInput {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseInvalidDate(Serializable data) {
        this("日期/时间格式不正确", data);
    }
    public JsonResponseInvalidDate(String message, Serializable data) {
        this(message, DESC_INVALID_DATE, data);
    }
    public JsonResponseInvalidDate(String message, String desc, Serializable data) {
        super(CODE_INVALID_DATE, message, desc, data);
    }

}
