package org.firas.sms.datatype;

public class AppNotFoundException extends RuntimeException {

    public AppNotFoundException() {
        this("要查找的短信应用不存在");
    }

    public AppNotFoundException(String message) {
        super(message);
    }

}
