package org.firas.common.response.expired;

import java.io.Serializable;

import org.firas.common.response.JsonResponse;

public class JsonResponseExpired extends JsonResponse {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseExpired(Serializable data) {
        super(CODE_EXPIRED, "超过期限", DESC_EXPIRED, data);
    }
    public JsonResponseExpired(String message, Serializable data) {
        super(CODE_EXPIRED, message, DESC_EXPIRED, data);
    }
    public JsonResponseExpired(String message, String desc, Serializable data) {
        super(CODE_EXPIRED, message, desc, data);
    }
    protected JsonResponseExpired(int code, String message, Serializable data) {
        super(code, message, DESC_EXPIRED, data);
    }
    protected JsonResponseExpired(int code, String message,
            String desc, Serializable data) {
        super(code, message, desc, data);
    }
}
