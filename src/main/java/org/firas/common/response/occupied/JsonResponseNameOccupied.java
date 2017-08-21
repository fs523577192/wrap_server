package org.firas.common.response.occupied;

import java.io.Serializable;

public class JsonResponseNameOccupied extends JsonResponseOccupied {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseNameOccupied(Serializable data) {
        super(CODE_NAME_OCCUPIED, "此名称已被占用", DESC_NAME_OCCUPIED, data);
    }
    public JsonResponseNameOccupied(String message, Serializable data) {
        super(CODE_NAME_OCCUPIED, message, DESC_NAME_OCCUPIED, data);
    }
    public JsonResponseNameOccupied(String message, String desc, Serializable data) {
        super(CODE_NAME_OCCUPIED, message, desc, data);
    }
}
