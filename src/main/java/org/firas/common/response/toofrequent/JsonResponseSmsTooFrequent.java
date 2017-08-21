package org.firas.common.response.toofrequent;

import java.io.Serializable;

public class JsonResponseSmsTooFrequent extends JsonResponseTooFrequent {

    private static final long serialVersionUID = 1L;

    public JsonResponseSmsTooFrequent(Serializable data) {
        super(CODE_SMS_TOO_FREQUENT, "发送短信次数过多",
                DESC_SMS_TOO_FREQUENT, data);
    }
    public JsonResponseSmsTooFrequent(String message, Serializable data) {
        super(CODE_SMS_TOO_FREQUENT, message, DESC_SMS_TOO_FREQUENT, data);
    }
    public JsonResponseSmsTooFrequent(String message, String desc,
            Serializable data) {
        super(CODE_SMS_TOO_FREQUENT, message, desc, data);
    }
}
