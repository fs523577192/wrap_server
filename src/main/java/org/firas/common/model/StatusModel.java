package org.firas.common.model;

import java.util.Map;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import org.firas.common.helper.TrueValueHelper;

@MappedSuperclass
public abstract class StatusModel extends TimeModel {

    public static final byte STATUS_DELETED = 0;
    public static final byte STATUS_NORMAL = 1;
    public static final byte STATUS_EXAMINING = 2;
    public static final byte STATUS_EDITING = 3;
    public static final byte STATUS_FROZEN = 4;
    public static final byte STATUS_UPLOADING = 5;
    public static final byte STATUS_INACTIVE = 6;
    public static final byte STATUS_USED = 7;
    public static final byte STATUS_EXPIRED = 8;

    @Column(nullable = false)
    protected byte status = STATUS_NORMAL;

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public boolean isStatusDeleted() {
        return STATUS_DELETED == status;
    }

    public boolean isStatusNormal() {
        return STATUS_NORMAL == status;
    }

    public boolean isStatusExamining() {
        return STATUS_EXAMINING == status;
    }

    public boolean isStatusEditing() {
        return STATUS_EDITING == status;
    }

    public boolean isStatusFrozen() {
        return STATUS_FROZEN == status;
    }

    public boolean isStatusUploading() {
        return STATUS_UPLOADING == status;
    }

    public boolean isStatusInactive() {
        return STATUS_INACTIVE == status;
    }

    public boolean isStatusUsed() {
        return STATUS_USED == status;
    }

    public boolean isStatusExpired() {
        return STATUS_EXPIRED == status;
    }


    public StatusModel statusDeleted() {
        status = STATUS_DELETED;
        return this;
    }

    public StatusModel statusNormal() {
        status = STATUS_NORMAL;
        return this;
    }

    public StatusModel statusExamining() {
        status = STATUS_EXAMINING;
        return this;
    }

    public StatusModel statusEditing() {
        status = STATUS_EDITING;
        return this;
    }

    public StatusModel statusFrozen() {
        status = STATUS_FROZEN;
        return this;
    }

    public StatusModel statusUploading() {
        status = STATUS_UPLOADING;
        return this;
    }

    public StatusModel statusInactive() {
        status = STATUS_INACTIVE;
        return this;
    }

    public StatusModel statusUsed() {
        status = STATUS_USED;
        return this;
    }

    public StatusModel statusExpired() {
        status = STATUS_EXPIRED;
        return this;
    }


    public String getStatusInfo() {
        switch (status) {
            case STATUS_DELETED:
                return "Deleted";
            case STATUS_NORMAL:
                return "Normal";
            case STATUS_EXAMINING:
                return "Examining";
            case STATUS_EDITING:
                return "Editing";
            case STATUS_FROZEN:
                return "Frozen";
            case STATUS_UPLOADING:
                return "Uploading";
            case STATUS_INACTIVE:
                return "Inactive";
            case STATUS_USED:
                return "Used";
            case STATUS_EXPIRED:
                return "Expired";
        }
        return "Undefined";
    }

    @Override
    public HashMap<String, Object> toMap(Map<String, Object> options) {
        HashMap<String, Object> result = super.toMap(options);
        if (TrueValueHelper.isTrue(options, "status")) {
            result.put("status", this.getStatusInfo());
        }
        return result;
    }
}
