package org.firas.jiadian.datatype;

import lombok.Getter;

public class ActivityIdNotFoundException
        extends ActivityNotFoundException {

    public ActivityIdNotFoundException(int activityId, String message) {
        super(message);
        this.activityId = activityId;
    }

    @Getter private int activityId;
}
