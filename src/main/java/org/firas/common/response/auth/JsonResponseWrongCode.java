package org.firas.common.response.auth;

import java.io.Serializable;

import org.firas.common.response.JsonResponse;

public class JsonResponseWrongCode extends JsonResponse {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseWrongCode(Serializable data) {
        super(CODE_WRONG_CODE, "验证码不正确", DESC_WRONG_CODE, data);
    }
    public JsonResponseWrongCode(String message, Serializable data) {
        super(CODE_WRONG_CODE, message, DESC_WRONG_CODE, data);
    }
    public JsonResponseWrongCode(String message, String desc, Serializable data) {
        super(CODE_WRONG_CODE, message, desc, data);
    }
}
