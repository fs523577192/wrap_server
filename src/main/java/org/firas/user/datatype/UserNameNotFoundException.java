package org.firas.user.datatype;

import lombok.Getter;

public class UserNameNotFoundException
        extends org.apache.shiro.authc.UnknownAccountException {

    @Getter private String userName;

    public UserNameNotFoundException(String userName) {
        this(userName, "该账号不存在");
    }

    public UserNameNotFoundException(String userName, String message) {
        super(message);
        this.userName = userName;
    }
}
