package org.firas.common.datatype;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Mutable<T> {

    @Getter @Setter protected T value;

    public boolean isNull() {
        return null == value;
    }

    public void setNull() {
        value = null;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
