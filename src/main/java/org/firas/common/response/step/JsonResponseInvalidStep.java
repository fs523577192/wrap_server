package org.firas.common.response.step;

import java.io.Serializable;
import org.firas.common.response.JsonResponse;

public class JsonResponseInvalidStep extends JsonResponse {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseInvalidStep(Serializable data) {
        super(CODE_INVALID_STEP, "", DESC_INVALID_STEP, data);
    }

    public JsonResponseInvalidStep(String message, Serializable data) {
        super(CODE_INVALID_STEP, message, DESC_INVALID_STEP, data);
    }

    public JsonResponseInvalidStep(String message, String desc, Serializable data) {
        super(CODE_INVALID_STEP, message, desc, data);
    }

    protected JsonResponseInvalidStep(int code, String message, Serializable data) {
        super(code, message, DESC_INVALID_STEP, data);
    }
    protected JsonResponseInvalidStep(int code, String message, String desc, Serializable data) {
        super(code, message, desc, data);
    }
}
