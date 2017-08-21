package org.firas.common.response;

import java.io.Serializable;

public class JsonResponseFailInsufficient extends JsonResponse {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseFailInsufficient(Serializable data) {
        super(CODE_FAIL_INSUFFICIENT, "", DESC_FAIL_INSUFFICIENT, data);
    }
    public JsonResponseFailInsufficient(String message, Serializable data) {
        super(CODE_FAIL_INSUFFICIENT, message, DESC_FAIL_INSUFFICIENT, data);
    }
    public JsonResponseFailInsufficient(String message, String desc, Serializable data) {
        super(CODE_FAIL_INSUFFICIENT, message, desc, data);
    }
}
