package org.firas.common.response;

import java.io.Serializable;

public class JsonResponseNetworkError extends JsonResponse {

    private static final long serialVersionUID = 1L;
    
    public JsonResponseNetworkError(Serializable data) {
        super(CODE_NETWORK_ERROR, "网络似乎出现了问题",
                DESC_NETWORK_ERROR, data);
    }
    public JsonResponseNetworkError(String message, Serializable data) {
        super(CODE_NETWORK_ERROR, message, DESC_NETWORK_ERROR, data);
    }
    public JsonResponseNetworkError(
            String message, String desc, Serializable data) {
        super(CODE_NETWORK_ERROR, message, desc, data);
    }
}
