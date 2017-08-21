package org.firas.common.response.input;

import java.io.Serializable;

public class JsonResponseInputTooSmall extends JsonResponseInvalidInput {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseInputTooSmall(Serializable data) {
        super(CODE_INPUT_TOO_SMALL, "输入的值太小", DESC_INPUT_SMALL, data);
    }
    public JsonResponseInputTooSmall(String message, Serializable data) {
        super(CODE_INPUT_TOO_SMALL, message, DESC_INPUT_SMALL, data);
    }
    public JsonResponseInputTooSmall(String message, String desc, Serializable data) {
        super(CODE_INPUT_TOO_SMALL, message, desc, data);
    }
}
