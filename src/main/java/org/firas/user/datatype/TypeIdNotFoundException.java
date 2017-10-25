package org.firas.user.datatype;

import lombok.Getter;

public class TypeIdNotFoundException extends UserTypeNotFoundException {

    @Getter private Integer typeId;

    public TypeIdNotFoundException(Integer typeId) {
        this(typeId, "该ID的用户类型不存在");
    }

    public TypeIdNotFoundException(Integer typeId, String message) {
        super(message);
        this.typeId = typeId;
    }
}
