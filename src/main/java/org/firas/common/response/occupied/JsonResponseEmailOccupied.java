package org.firas.common.response.occupied;

import java.io.Serializable;

public class JsonResponseEmailOccupied extends JsonResponseOccupied {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseEmailOccupied(Serializable data) {
        super(CODE_EMAIL_OCCUPIED, "此电子邮箱已被其他用户使用", DESC_EMAIL_OCCUPIED, data);
    }
    public JsonResponseEmailOccupied(String message, Serializable data) {
        super(CODE_EMAIL_OCCUPIED, message, DESC_EMAIL_OCCUPIED, data);
    }
    public JsonResponseEmailOccupied(String message, String desc, Serializable data) {
        super(CODE_EMAIL_OCCUPIED, message, desc, data);
    }
}
