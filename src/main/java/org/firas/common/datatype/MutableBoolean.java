package org.firas.common.datatype;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MutableBoolean extends Mutable<Boolean> {

    public MutableBoolean(Boolean value) {
        this.setValue(value);
    }

    public void setTrue() {
        value = true;
    }

    public void setFalse() {
        value = false;
    }
}
