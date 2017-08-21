package org.firas.common.response.expired;

import java.io.Serializable;

public class JsonResponseSmsExpired extends JsonResponseExpired {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseSmsExpired(Serializable data) {
        super(CODE_SMS_EXPIRED, "短信验证码已过期", DESC_SMS_EXPIRED, data);
    }
    public JsonResponseSmsExpired(String message, Serializable data) {
        super(CODE_SMS_EXPIRED, message, DESC_SMS_EXPIRED, data);
    }
    public JsonResponseSmsExpired(String message, String desc, Serializable data) {
        super(CODE_SMS_EXPIRED, message, desc, data);
    }
}
