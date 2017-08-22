package org.firas.wrap.datatype;

import lombok.Getter;

/**
 *
 */
public class BoxNameNotUniqueException extends Exception {

    public BoxNameNotUniqueException(String boxName, String message) {
        super(message);
        this.boxName = boxName;
    }

    @Getter private String boxName;
}
