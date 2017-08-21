package org.firas.common.response.input;

import java.io.Serializable;

public class JsonResponseInvalidMobile extends JsonResponseInvalidInput {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseInvalidMobile(Serializable data) {
        this("手机号格式不正确", data);
    }
    public JsonResponseInvalidMobile(String message, Serializable data) {
        this(message, DESC_INVALID_MOBILE, data);
    }
    public JsonResponseInvalidMobile(String message, String desc, Serializable data) {
        super(CODE_INVALID_MOBILE, message, desc, data);
    }
}
