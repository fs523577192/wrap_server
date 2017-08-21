package org.firas.common.response.auth;

import java.io.Serializable;

import org.firas.common.response.JsonResponse;

public class JsonResponseNoPermission extends JsonResponse {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseNoPermission(Serializable data) {
        super(CODE_NO_PERMISSION, "您不能使用此功能", DESC_NO_PERMISSION, data);
    }
    public JsonResponseNoPermission(String message, Serializable data) {
        super(CODE_NO_PERMISSION, message, DESC_NO_PERMISSION, data);
    }
    public JsonResponseNoPermission(String message, String desc, Serializable data) {
        super(CODE_NO_PERMISSION, message, desc, data);
    }
}
