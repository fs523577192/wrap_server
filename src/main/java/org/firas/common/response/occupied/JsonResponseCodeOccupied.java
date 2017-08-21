package org.firas.common.response.occupied;

import java.io.Serializable;

public class JsonResponseCodeOccupied extends JsonResponseOccupied {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseCodeOccupied(Serializable data) {
        super(CODE_CODE_OCCUPIED, "此代码已被占用", DESC_CODE_OCCUPIED, data);
    }
    public JsonResponseCodeOccupied(String message, Serializable data) {
        super(CODE_CODE_OCCUPIED, message, DESC_CODE_OCCUPIED, data);
    }
    public JsonResponseCodeOccupied(String message, String desc, Serializable data) {
        super(CODE_CODE_OCCUPIED, message, desc, data);
    }
}
