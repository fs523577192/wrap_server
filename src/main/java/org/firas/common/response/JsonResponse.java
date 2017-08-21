package org.firas.common.response;

import java.util.Date;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

public class JsonResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Getter private long timestamp;
    @Getter protected int code;
    @Getter protected String message;
    @Getter protected String desc;
    @Getter protected Serializable data;

    public JsonResponse(int code, String message, String desc, Serializable data) {
        timestamp = new Date().getTime();
        this.code = code;
        this.message = message;
        this.desc = desc;
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return CODE_SUCCESS == this.getCode();
    }


    public static final int CODE_SUCCESS = 0;
    public static final String DESC_SUCCESS = "Success";
    
    
    public static final int CODE_NEED_LOGIN = 10;
    public static final String DESC_NEED_LOGIN = "Login Required";
    
    
    public static final int CODE_WRONG_PASSWORD = 100;
    public static final String DESC_WRONG_PASSWORD = "Wrong Password";

    public static final int CODE_WRONG_TOKEN = 200;
    public static final String DESC_WRONG_TOKEN = "Wrong Token";

    public static final int CODE_WRONG_CODE = 1000;
    public static final String DESC_WRONG_CODE = "Wrong Code";

    public static final int CODE_USER_FROZEN = 2000;
    public static final String DESC_USER_FROZEN = "User Frozen";


    public static final int CODE_OCCUPIED = 10000;
    public static final String DESC_OCCUPIED = "已被占用";
    
    public static final int CODE_USER_NAME_OCCUPIED = 10100;
    public static final String DESC_USER_NAME_OCCUPIED = "The User Name Has Been Used";
    
    public static final int CODE_MOBILE_OCCUPIED = 10200;
    public static final String DESC_MOBILE_OCCUPIED = "The Mobile Has Been Used";
    
    public static final int CODE_EMAIL_OCCUPIED = 10300;
    public static final String DESC_EMAIL_OCCUPIED = "The Email Has Been Used";
    
    public static final int CODE_CODE_OCCUPIED = 10400;
    public static final String DESC_CODE_OCCUPIED = "The Code Has Been Used";
    
    public static final int CODE_NAME_OCCUPIED = 10500;
    public static final String DESC_NAME_OCCUPIED = "The Name Has Been Used";
    
    
    public static final int CODE_NOT_FOUND = 20000;
    public static final String DESC_NOT_FOUND = "不存在";

    public static final int CODE_USER_NOT_FOUND = 20100;
    public static final String DESC_USER_NOT_FOUND = "用户不存在";

    public static final int CODE_USER_TYPE_NOT_FOUND = 20110;
    public static final String DESC_USER_TYPE_NOT_FOUND = "用户类型不存在";


    public static final int CODE_EXPIRED = 30000;
    public static final String DESC_EXPIRED = "超过期限";

    public static final int CODE_SMS_EXPIRED = 30100;
    public static final String DESC_SMS_EXPIRED = "短信验证码超时失效";


    public static final int CODE_NETWORK_ERROR = 40000;
    public static final String DESC_NETWORK_ERROR = "网络访问失败";


    public static final int CODE_OUT_OF_LIMIT = 50000;
    public static final String DESC_OUT_OF_LIMIT = "超出限制";

    public static final int CODE_UPPER_LIMIT = 51000;
    public static final String DESC_UPPER_LIMIT = "高于上限";

    public static final int CODE_TOO_FREQUENT = 51100;
    public static final String DESC_TOO_FREQUENT = "过于频繁";

    public static final int CODE_SMS_TOO_FREQUENT = 51110;
    public static final String DESC_SMS_TOO_FREQUENT = "发送短信过于频繁";

    public static final int CODE_LOWER_LIMIT = 52000;
    public static final String DESC_LOWER_LIMIT = "低于下限";


    public static final int CODE_NO_PERMISSION = -10;
    public static final String DESC_NO_PERMISSION = "No Permission";
    
    
    public static final int CODE_FAIL_UNDEFINED = -10000;
    public static final String DESC_FAIL_UNDEFINED = "Undefined Failure";
    
    
    public static final int CODE_INVALID_INPUT = -20000;
    public static final String DESC_INVALID_INPUT = "Invalid Input";
    
    public static final int CODE_INVALID_MOBILE = -20001;
    public static final String DESC_INVALID_MOBILE = "Invalid Mobile";
    
    public static final int CODE_INVALID_EMAIL = -20002;
    public static final String DESC_INVALID_EMAIL = "Invalid Email";
    
    public static final int CODE_INVALID_DATE = -20010;
    public static final String DESC_INVALID_DATE = "Invalid Date Or Time";
    
    public static final int CODE_INVALID_IMAGE = -20020;
    public static final String DESC_INVALID_IMAGE = "Invalid Image";
    
    
    public static final int CODE_INPUT_TOO_SHORT = -20100;
    public static final String DESC_INPUT_SHORT = "An Input Field Is Too Short";
    
    public static final int CODE_INPUT_TOO_LONG = -20200;
    public static final String DESC_INPUT_LONG = "An Input Field Is Too Long";
    
    public static final int CODE_INPUT_TOO_LARGE = -20300;
    public static final String DESC_INPUT_LARGE = "An Numeric Input Field Is Too Large";
    
    public static final int CODE_INPUT_TOO_SMALL = -20400;
    public static final String DESC_INPUT_SMALL = "An Numeric Input Field Is Too Small";


    public static final int CODE_INVALID_STEP = -30000;
    public static final String DESC_INVALID_STEP = "Invalid Step";

    public static final int CODE_CAPTCHA_NOT_REQUESTED = -30100;
    public static final String DESC_CAPTCHA_NOT_REQUESTED = "The captcha code has not been requested";

    public static final int CODE_SMS_NOT_REQUESTED = -30110;
    public static final String DESC_SMS_NOT_REQUESTED = "The sms verification code has not been requested";

    public static final int CODE_EMAIL_NOT_REQUESTED = -30120;
    public static final String DESC_EMAIL_NOT_REQUESTED = "The verification URL in the email has not been requested";


    public static final int CODE_FAIL_INSUFFICIENT = -40000;
    public static final String DESC_FAIL_INSUFFICIENT = "Insufficient";

    
}
