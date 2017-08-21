package org.firas.common.response.step;

import java.io.Serializable;

public class JsonResponseEmailNotRequested extends JsonResponseInvalidStep {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseEmailNotRequested(Serializable data) {
        super(CODE_EMAIL_NOT_REQUESTED, "", DESC_EMAIL_NOT_REQUESTED, data);
    }

    public JsonResponseEmailNotRequested(String message, Serializable data) {
        super(CODE_EMAIL_NOT_REQUESTED, message, DESC_EMAIL_NOT_REQUESTED, data);
    }

    public JsonResponseEmailNotRequested(String message, String desc, Serializable data) {
        super(CODE_EMAIL_NOT_REQUESTED, message, desc, data);
    }
}
