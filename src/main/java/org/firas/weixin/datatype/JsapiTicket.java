package org.firas.weixin.datatype;

import java.io.Serializable;
import lombok.Data;

/**
 * https://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
 */
@Data
public class JsapiTicket implements Serializable {
    
    public static final int CODE_SUCCESS = 0;
    public static final String MSG_SUCCESS = "ok";

    private int errcode;

    private String errmsg;

    private String ticket;

    private int expires_in = 7200;
    public int getExpiresIn() {
        return expires_in;
    }
}
