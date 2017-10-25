package org.firas.weixin.datatype;

import lombok.Getter;
import org.firas.weixin.model.WeixinApp;

public class AppNameOccupiedException extends RuntimeException {

    @Getter private WeixinApp app;

    public AppNameOccupiedException(WeixinApp app) {
        this(app, "该名称的微信应用已存在");
    }

    public AppNameOccupiedException(WeixinApp app, String message) {
        super(message);
        this.app = app;
    }
}
