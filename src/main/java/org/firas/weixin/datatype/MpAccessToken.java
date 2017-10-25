package org.firas.weixin.datatype;

import java.io.Serializable;
import lombok.Data;

/**
 * https://mp.weixin.qq.com/wiki/15/54ce45d8d30b6bf6758f68d2e95bc627.html
 */
@Data
public class MpAccessToken implements Serializable {
    
    public static final int CODE_SUCCESS = 0;
    public static final String MSG_SUCCESS = "ok";

    private Integer errcode;

    private String errmsg;

    private String access_token;
    public String getToken() {
        return access_token;
    }

    private int expires_in = 7200;
    public int getExpiresIn() {
        return expires_in;
    }
}
