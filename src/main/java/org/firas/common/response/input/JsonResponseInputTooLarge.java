package org.firas.common.response.input;

import java.io.Serializable;

public class JsonResponseInputTooLarge extends JsonResponseInvalidInput {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseInputTooLarge(Serializable data) {
        super(CODE_INPUT_TOO_LARGE, "输入的值过大", DESC_INPUT_LARGE, data);
    }
    public JsonResponseInputTooLarge(String message, Serializable data) {
        super(CODE_INPUT_TOO_LARGE, message, DESC_INPUT_LARGE, data);
    }
    public JsonResponseInputTooLarge(String message, String desc, Serializable data) {
        super(CODE_INPUT_TOO_LARGE, message, desc, data);
    }
}
