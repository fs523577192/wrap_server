package org.firas.wrap.datatype;

import lombok.Getter;

/**
 *
 */
public class ComponentNameNotUniqueException extends Exception {

    public ComponentNameNotUniqueException(String componentName, String message) {
        super(message);
        this.componentName = componentName;
    }

    @Getter private String componentName;
}
