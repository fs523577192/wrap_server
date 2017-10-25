package org.firas.weixin.datatype;

/**
 * Created by lc114 on 2017/7/7.
 */
import java.io.Serializable;
import java.util.HashMap;

import lombok.Data;

@Data
public class MpUserInfo implements Serializable{
    public static final int CODE_SUCCESS = 0;
    public static final String MSG_SUCCESS = "ok";

    private Integer errcode;

    private String errmsg;

    private String openid;
    public String getOpenid() {return openid;}

    private String nickname;

    private String sex;

    private String province;

    private String city;

    private String country;

    private String language;

    private String headimgurl;
    public String getHeadimgurl() {return headimgurl;}

    private String [] privilege;

    private String unionid;
    public String getUnionid() {return unionid;}

    public HashMap<String, Object> getMap() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("openid", getOpenid());
        map.put("unionid", getUnionid());
        map.put("nickname", nickname);
        map.put("headimgurl", headimgurl);
        return map;
    }
}
