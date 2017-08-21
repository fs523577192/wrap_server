package org.firas.common.response;

import java.io.Serializable;

public class JsonResponseFailUndefined extends JsonResponse {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseFailUndefined(Serializable data) {
        super(CODE_FAIL_UNDEFINED, "", DESC_FAIL_UNDEFINED, data);
    }
    public JsonResponseFailUndefined(String message, Serializable data) {
        super(CODE_FAIL_UNDEFINED, message, DESC_FAIL_UNDEFINED, data);
    }
    public JsonResponseFailUndefined(String message, String desc, Serializable data) {
        super(CODE_FAIL_UNDEFINED, message, desc, data);
    }
}
