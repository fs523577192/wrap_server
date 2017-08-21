package org.firas.common.response.notfound;

import java.io.Serializable;

public class JsonResponseUserNotFound extends JsonResponseNotFound {

    private static final long serialVersionUID = 1L;

    public JsonResponseUserNotFound(Serializable data) {
        super(CODE_USER_NOT_FOUND, "账号不存在", DESC_USER_NOT_FOUND, data);
    }
    public JsonResponseUserNotFound(String message, Serializable data) {
        super(CODE_USER_NOT_FOUND, message, DESC_USER_NOT_FOUND, data);
    }
    public JsonResponseUserNotFound(String message, String desc, Serializable data) {
        super(CODE_USER_NOT_FOUND, message, desc, data);
    }
}
