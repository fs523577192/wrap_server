package org.firas.common.response.step;

import java.io.Serializable;

public class JsonResponseCaptchaNotRequested extends JsonResponseInvalidStep {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseCaptchaNotRequested(Serializable data) {
        super(CODE_CAPTCHA_NOT_REQUESTED, "", DESC_CAPTCHA_NOT_REQUESTED, data);
    }

    public JsonResponseCaptchaNotRequested(String message, Serializable data) {
        super(CODE_CAPTCHA_NOT_REQUESTED, message, DESC_CAPTCHA_NOT_REQUESTED, data);
    }

    public JsonResponseCaptchaNotRequested(String message, String desc, Serializable data) {
        super(CODE_CAPTCHA_NOT_REQUESTED, message, desc, data);
    }
}
