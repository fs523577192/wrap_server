package org.firas.common.response.auth;

import java.io.Serializable;

import org.firas.common.response.JsonResponse;

public class JsonResponseWrongPassword extends JsonResponse {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseWrongPassword(Serializable data) {
        super(CODE_WRONG_PASSWORD, "密码不正确", DESC_WRONG_PASSWORD, data);
    }
    public JsonResponseWrongPassword(String message, Serializable data) {
        super(CODE_WRONG_PASSWORD, message, DESC_WRONG_PASSWORD, data);
    }
    public JsonResponseWrongPassword(String message, String desc, Serializable data) {
        super(CODE_WRONG_PASSWORD, message, desc, data);
    }
}
