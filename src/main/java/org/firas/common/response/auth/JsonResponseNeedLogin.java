package org.firas.common.response.auth;

import java.io.Serializable;

import org.firas.common.response.JsonResponse;

public class JsonResponseNeedLogin extends JsonResponse {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseNeedLogin(Serializable data) {
        super(CODE_NEED_LOGIN, "请重新登录", DESC_NEED_LOGIN, data);
    }
    public JsonResponseNeedLogin(String message, Serializable data) {
        super(CODE_NEED_LOGIN, message, DESC_NEED_LOGIN, data);
    }
    public JsonResponseNeedLogin(String message, String desc, Serializable data) {
        super(CODE_NEED_LOGIN, message, desc, data);
    }
}
