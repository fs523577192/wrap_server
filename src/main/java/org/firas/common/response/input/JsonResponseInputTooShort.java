package org.firas.common.response.input;

import java.io.Serializable;

public class JsonResponseInputTooShort extends JsonResponseInvalidInput {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseInputTooShort(Serializable data) {
        super(CODE_INPUT_TOO_SHORT, "输入的值太短", DESC_INPUT_SHORT, data);
    }
    public JsonResponseInputTooShort(String message, Serializable data) {
        super(CODE_INPUT_TOO_SHORT, message, DESC_INPUT_SHORT, data);
    }
    public JsonResponseInputTooShort(String message, String desc, Serializable data) {
        super(CODE_INPUT_TOO_SHORT, message, desc, data);
    }
}
