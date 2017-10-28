package org.firas.jiadian.datatype;

import lombok.Getter;

/**
 *
 */
public class ActivityNameNotUniqueException extends Exception {

    public ActivityNameNotUniqueException(String activityName, String message) {
        super(message);
        this.activityName = activityName;
    }

    @Getter private String activityName;
}
