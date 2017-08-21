package org.firas.common.datatype;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MutableShort extends MutableNumber<Short> {

    public MutableShort(Short value) {
        this.setValue(value);
    }

    public boolean isZero() {
        return 0 == value;
    }
    
    public boolean isPositive() {
        return 0 < value;
    }

    public boolean isNegative() {
        return 0 > value;
    }

    public Short setZero() {
        return value = 0;
    }

    public Short setOne() {
        return value = 1;
    }

    public Short setNegativeOne() {
        return value = -1;
    }

    public Short negate() {
        if (isNull()) return null;
        return value = (short)(-value);
    }

    public Short add(Number n) {
        return value = (short)(value + n.shortValue());
    }

    public Short subtract(Number n) {
        return value = (short)(value - n.shortValue());
    }

    public Short multiply(Number n) {
        return value = (short)(value * n.shortValue());
    }

    public Short divide(Number n) {
        return value = (short)(value / n.shortValue());
    }
}
