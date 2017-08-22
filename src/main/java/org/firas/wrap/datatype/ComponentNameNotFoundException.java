package org.firas.wrap.datatype;

import lombok.Getter;

public class ComponentNameNotFoundException
        extends ComponentNotFoundException {

    public ComponentNameNotFoundException(String componentName, String message) {
        super(message);
        this.componentName = componentName;
    }

    @Getter private String componentName;
}
