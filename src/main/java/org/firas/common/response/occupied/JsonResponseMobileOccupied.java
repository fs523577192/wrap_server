package org.firas.common.response.occupied;

import java.io.Serializable;

public class JsonResponseMobileOccupied extends JsonResponseOccupied {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseMobileOccupied(Serializable data) {
        super(CODE_MOBILE_OCCUPIED, "此手机号已被其他用户使用", DESC_MOBILE_OCCUPIED, data);
    }
    public JsonResponseMobileOccupied(String message, Serializable data) {
        super(CODE_MOBILE_OCCUPIED, message, DESC_MOBILE_OCCUPIED, data);
    }
    public JsonResponseMobileOccupied(String message, String desc, Serializable data) {
        super(CODE_MOBILE_OCCUPIED, message, desc, data);
    }
}
