package org.firas.weixin.datatype;

/**
 * Created by lc114 on 2017/7/7.
 */
import java.io.Serializable;
import lombok.Data;

@Data
public class MpAuthorizeAccessToken implements Serializable{
    public static final int CODE_SUCCESS = 0;
    public static final String MSG_SUCCESS = "ok";

    private Integer errcode;

    private String errmsg;

    private String access_token;
    public String getAccessToken() {
        return access_token;
    }

    private int expires_in = 7200;
    public int getExpiresIn() {
        return expires_in;
    }

    private String refresh_token;
    public String getRefreshToken() {return refresh_token;}

    private String openid;
    public String getOpenid() {return openid;}

    private String scope;
    public String getScope() {return scope;}
}
