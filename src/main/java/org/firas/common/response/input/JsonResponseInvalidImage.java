package org.firas.common.response.input;

import java.io.Serializable;

public class JsonResponseInvalidImage extends JsonResponseInvalidInput {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseInvalidImage(Serializable data) {
        this("无法识别的图片格式", data);
    }
    public JsonResponseInvalidImage(String message, Serializable data) {
        this(message, DESC_INVALID_IMAGE, data);
    }
    public JsonResponseInvalidImage(String message, String desc, Serializable data) {
        super(CODE_INVALID_IMAGE, message, desc, data);
    }
}
