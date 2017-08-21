package org.firas.common.response.input;

import java.io.Serializable;

public class JsonResponseInvalidEmail extends JsonResponseInvalidInput {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseInvalidEmail(Serializable data) {
        this("电子邮箱地址格式不正确", data);
    }
    public JsonResponseInvalidEmail(String message, Serializable data) {
        this(message, DESC_INVALID_EMAIL, data);
    }
    public JsonResponseInvalidEmail(String message, String desc, Serializable data) {
        super(CODE_INVALID_EMAIL, message, desc, data);
    }
}
