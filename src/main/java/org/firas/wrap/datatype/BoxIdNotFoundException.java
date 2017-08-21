package org.firas.wrap.datatype;

import lombok.Getter;

public class BoxIdNotFoundException
        extends BoxNotFoundException {

    public BoxIdNotFoundException(int boxId, String message) {
        super(message);
        this.boxId = boxId;
    }

    @Getter private int boxId;
}
