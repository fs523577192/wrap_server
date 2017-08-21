package org.firas.common.response.step;

import java.io.Serializable;

public class JsonResponseSmsNotRequested extends JsonResponseInvalidStep {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseSmsNotRequested(Serializable data) {
        super(CODE_SMS_NOT_REQUESTED, "", DESC_SMS_NOT_REQUESTED, data);
    }

    public JsonResponseSmsNotRequested(String message, Serializable data) {
        super(CODE_SMS_NOT_REQUESTED, message, DESC_SMS_NOT_REQUESTED, data);
    }

    public JsonResponseSmsNotRequested(String message, String desc, Serializable data) {
        super(CODE_SMS_NOT_REQUESTED, message, desc, data);
    }
}
