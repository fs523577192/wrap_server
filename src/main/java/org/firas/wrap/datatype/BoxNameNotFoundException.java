package org.firas.wrap.datatype;

import lombok.Getter;

public class BoxNameNotFoundException
        extends BoxNotFoundException {

    public BoxNameNotFoundException(String boxName, String message) {
        super(message);
        this.boxName = boxName;
    }

    @Getter private String boxName;
}
