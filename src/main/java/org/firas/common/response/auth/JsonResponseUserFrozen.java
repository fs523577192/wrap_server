package org.firas.common.response.auth;

import java.io.Serializable;

import org.firas.common.response.JsonResponse;

public class JsonResponseUserFrozen extends JsonResponse {

    private static final long serialVersionUID = 1L;

    public JsonResponseUserFrozen(Serializable data) {
        super(CODE_USER_FROZEN, "账号已被冻结", DESC_USER_FROZEN, data);
    }
    public JsonResponseUserFrozen(String message, Serializable data) {
        super(CODE_USER_FROZEN, message, DESC_USER_FROZEN, data);
    }
    public JsonResponseUserFrozen(String message, String desc, Serializable data) {
        super(CODE_USER_FROZEN, message, desc, data);
    }
}
