package org.firas.common.response.toofrequent;

import java.io.Serializable;

import org.firas.common.response.JsonResponse;

public class JsonResponseTooFrequent extends JsonResponse {

    private static final long serialVersionUID = 1L;

    public JsonResponseTooFrequent(Serializable data) {
        super(CODE_TOO_FREQUENT, "次数过多", DESC_TOO_FREQUENT, data);
    }
    public JsonResponseTooFrequent(String message, Serializable data) {
        super(CODE_TOO_FREQUENT, message, DESC_TOO_FREQUENT, data);
    }
    public JsonResponseTooFrequent(String message, String desc, Serializable data) {
        super(CODE_TOO_FREQUENT, message, desc, data);
    }

    protected JsonResponseTooFrequent(int code, String message, Serializable data) {
        super(code, message, DESC_TOO_FREQUENT, data);
    }
    protected JsonResponseTooFrequent(int code, String message, String desc, Serializable data) {
        super(code, message, desc, data);
    }
}
