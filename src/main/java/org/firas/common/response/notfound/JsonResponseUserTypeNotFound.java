package org.firas.common.response.notfound;

import java.io.Serializable;

public class JsonResponseUserTypeNotFound extends JsonResponseNotFound {

    private static final long serialVersionUID = 1L;

    public JsonResponseUserTypeNotFound(Serializable data) {
        super(CODE_USER_TYPE_NOT_FOUND, "用户类型不存在", DESC_USER_TYPE_NOT_FOUND, data);
    }
    public JsonResponseUserTypeNotFound(String message, Serializable data) {
        super(CODE_USER_TYPE_NOT_FOUND, message, DESC_USER_TYPE_NOT_FOUND, data);
    }
    public JsonResponseUserTypeNotFound(String message, String desc, Serializable data) {
        super(CODE_USER_TYPE_NOT_FOUND, message, desc, data);
    }
}
