package org.firas.common.response.input;

import java.io.Serializable;

public class JsonResponseInputTooLong extends JsonResponseInvalidInput {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseInputTooLong(Serializable data) {
        super(CODE_INPUT_TOO_LONG, "输入的值太长", DESC_INPUT_LONG, data);
    }
    public JsonResponseInputTooLong(String message, Serializable data) {
        super(CODE_INPUT_TOO_LONG, message, DESC_INPUT_LONG, data);
    }
    public JsonResponseInputTooLong(String message, String desc, Serializable data) {
        super(CODE_INPUT_TOO_LONG, message, desc, data);
    }
}
