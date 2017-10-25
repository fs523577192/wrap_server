package org.firas.user.datatype;

public class UserTypeNotFoundException extends RuntimeException {

    public UserTypeNotFoundException() {
        super();
    }

    public UserTypeNotFoundException(String message) {
        super(message);
    }
}
