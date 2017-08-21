package org.firas.common.response.notfound;

import java.io.Serializable;

import org.firas.common.response.JsonResponse;

public class JsonResponseNotFound extends JsonResponse {

    private static final long serialVersionUID = 1L;

    public JsonResponseNotFound(Serializable data) {
        super(CODE_NOT_FOUND, "无法找到", DESC_NOT_FOUND, data);
    }
    public JsonResponseNotFound(String message, Serializable data) {
        super(CODE_NOT_FOUND, message, DESC_NOT_FOUND, data);
    }
    public JsonResponseNotFound(String message, String desc, Serializable data) {
        super(CODE_NOT_FOUND, message, desc, data);
    }

    protected JsonResponseNotFound(int code, String message, Serializable data) {
        super(code, message, DESC_NOT_FOUND, data);
    }
    protected JsonResponseNotFound(int code, String message, String desc, Serializable data) {
        super(code, message, desc, data);
    }
}
