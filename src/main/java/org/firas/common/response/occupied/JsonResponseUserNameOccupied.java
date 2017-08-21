package org.firas.common.response.occupied;

import java.io.Serializable;

public class JsonResponseUserNameOccupied extends JsonResponseOccupied {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseUserNameOccupied(Serializable data) {
        super(CODE_USER_NAME_OCCUPIED, "此账号已存在", DESC_USER_NAME_OCCUPIED, data);
    }
    public JsonResponseUserNameOccupied(String message, Serializable data) {
        super(CODE_USER_NAME_OCCUPIED, message, DESC_USER_NAME_OCCUPIED, data);
    }
    public JsonResponseUserNameOccupied(String message, String desc, Serializable data) {
        super(CODE_USER_NAME_OCCUPIED, message, desc, data);
    }
}
