package org.firas.common.response.occupied;

import java.io.Serializable;

import org.firas.common.response.JsonResponse;

public class JsonResponseOccupied extends JsonResponse {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseOccupied(Serializable data) {
        super(CODE_OCCUPIED, "已被其他用户占用", DESC_OCCUPIED, data);
    }
    public JsonResponseOccupied(String message, Serializable data) {
        super(CODE_OCCUPIED, message, DESC_OCCUPIED, data);
    }
    public JsonResponseOccupied(String message, String desc, Serializable data) {
        super(CODE_OCCUPIED, message, desc, data);
    }
    protected JsonResponseOccupied(int code, String message, Serializable data) {
        super(code, message, DESC_OCCUPIED, data);
    }
    protected JsonResponseOccupied(int code, String message,
            String desc, Serializable data) {
        super(code, message, desc, data);
    }
}
