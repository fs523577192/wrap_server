package org.firas.wrap.datatype;

import lombok.Getter;

public class ComponentIdNotFoundException
        extends ComponentNotFoundException {

    public ComponentIdNotFoundException(int componentId, String message) {
        super(message);
        this.componentId = componentId;
    }

    @Getter private int componentId;
}
