package org.firas.common.response;

import java.io.Serializable;

public class JsonResponseSuccess extends JsonResponse {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseSuccess(Serializable data) {
        super(CODE_SUCCESS, "", DESC_SUCCESS, data);
    }
    public JsonResponseSuccess(String message, Serializable data) {
        super(CODE_SUCCESS, message, DESC_SUCCESS, data);
    }
    public JsonResponseSuccess(String message, String desc, Serializable data) {
        super(CODE_SUCCESS, message, desc, data);
    }
}
