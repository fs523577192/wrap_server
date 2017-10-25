package org.firas.weixin.datatype;

public class AppNotFoundException extends RuntimeException {

    public AppNotFoundException() {
        this("该微信应用不存在");
    }

    public AppNotFoundException(String message) {
        super(message);
    }
}

