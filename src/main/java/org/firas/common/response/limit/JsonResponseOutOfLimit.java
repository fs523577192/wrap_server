package org.firas.common.response.limit;

import java.io.Serializable;

import org.firas.common.response.JsonResponse;

public class JsonResponseOutOfLimit extends JsonResponse {

    private static final long serialVersionUID = 1L;

    public JsonResponseOutOfLimit(Serializable data) {
        super(CODE_OUT_OF_LIMIT, "超过限制", DESC_OUT_OF_LIMIT, data);
    }
    public JsonResponseOutOfLimit(String message, Serializable data) {
        super(CODE_OUT_OF_LIMIT, message, DESC_OUT_OF_LIMIT, data);
    }
    public JsonResponseOutOfLimit(String message, String desc, Serializable data) {
        super(CODE_OUT_OF_LIMIT, message, desc, data);
    }

    protected JsonResponseOutOfLimit(int code, String message, Serializable data) {
        super(code, message, DESC_OUT_OF_LIMIT, data);
    }
    protected JsonResponseOutOfLimit(int code, String message, String desc, Serializable data) {
        super(code, message, desc, data);
    }
}
