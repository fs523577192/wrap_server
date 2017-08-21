package org.firas.common.response.auth;

import java.io.Serializable;

import org.firas.common.response.JsonResponse;

public class JsonResponseWrongToken extends JsonResponse {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseWrongToken(Serializable data) {
        super(CODE_WRONG_TOKEN, "令牌不正确", DESC_WRONG_TOKEN, data);
    }
    public JsonResponseWrongToken(String message, Serializable data) {
        super(CODE_WRONG_TOKEN, message, DESC_WRONG_TOKEN, data);
    }
    public JsonResponseWrongToken(String message, String desc, Serializable data) {
        super(CODE_WRONG_TOKEN, message, desc, data);
    }
}
