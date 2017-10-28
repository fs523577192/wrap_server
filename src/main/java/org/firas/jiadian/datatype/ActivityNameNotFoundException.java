package org.firas.jiadian.datatype;

import lombok.Getter;

public class ActivityNameNotFoundException
        extends ActivityNotFoundException {

    public ActivityNameNotFoundException(String activityName, String message) {
        super(message);
        this.activityName = activityName;
    }

    @Getter private String activityName;
}
