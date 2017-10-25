package org.firas.user.datatype;

import lombok.Getter;
import org.firas.user.model.UserBase;

public class UserNameOccupiedException extends RuntimeException {

    @Getter private UserBase user;

    public UserNameOccupiedException(UserBase user) {
        this(user, "该账号已存在");
    }

    public UserNameOccupiedException(UserBase user, String message) {
        super(message);
        this.user = user;
    }
}
