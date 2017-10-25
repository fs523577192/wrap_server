package org.firas.user.datatype;

import lombok.Getter;

public class UserIdNotFoundException extends Exception {

    @Getter private Integer userId;

    public UserIdNotFoundException(Integer userId) {
        this(userId, "该ID的用户不存在");
    }

    public UserIdNotFoundException(Integer userId, String message) {
        super(message);
        this.userId = userId;
    }
}
