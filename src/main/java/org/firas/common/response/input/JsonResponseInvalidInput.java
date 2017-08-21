package org.firas.common.response.input;

import java.io.Serializable;

import org.firas.common.response.JsonResponse;

public class JsonResponseInvalidInput extends JsonResponse {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseInvalidInput(Serializable data) {
        super(CODE_INVALID_INPUT, "无效的输入", DESC_INVALID_INPUT, data);
    }
    public JsonResponseInvalidInput(String message, Serializable data) {
        super(CODE_INVALID_INPUT, message, DESC_INVALID_INPUT, data);
    }
    public JsonResponseInvalidInput(String message, String desc, Serializable data) {
        super(CODE_INVALID_INPUT, message, desc, data);
    }
    protected JsonResponseInvalidInput(int code, String message, Serializable data) {
        super(code, message, DESC_INVALID_INPUT, data);
    }
    protected JsonResponseInvalidInput(int code, String message, String desc, Serializable data) {
        super(code, message, desc, data);
    }
}
