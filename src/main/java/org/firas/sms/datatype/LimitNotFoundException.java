package org.firas.sms.datatype;

public class LimitNotFoundException extends RuntimeException {

    public LimitNotFoundException() {
        this("要查找的短信发送限制不存在");
    }

    public LimitNotFoundException(String message) {
        super(message);
    }

}
