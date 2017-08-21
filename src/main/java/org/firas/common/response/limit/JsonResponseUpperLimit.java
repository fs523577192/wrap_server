package org.firas.common.response.limit;

import java.io.Serializable;

import org.firas.common.response.JsonResponse;

public class JsonResponseUpperLimit extends JsonResponse {

    private static final long serialVersionUID = 1L;

    public JsonResponseUpperLimit(Serializable data) {
        super(CODE_UPPER_LIMIT, "高于上限", DESC_UPPER_LIMIT, data);
    }
    public JsonResponseUpperLimit(String message, Serializable data) {
        super(CODE_UPPER_LIMIT, message, DESC_UPPER_LIMIT, data);
    }
    public JsonResponseUpperLimit(String message, String desc, Serializable data) {
        super(CODE_UPPER_LIMIT, message, desc, data);
    }

    protected JsonResponseUpperLimit(int code, String message, Serializable data) {
        super(code, message, DESC_UPPER_LIMIT, data);
    }
    protected JsonResponseUpperLimit(int code, String message, String desc, Serializable data) {
        super(code, message, desc, data);
    }
}
