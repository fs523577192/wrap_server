package org.firas.common.datatype;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MutableLong extends MutableNumber<Long> {

    public MutableLong(Long value) {
        this.setValue(value);
    }

    public boolean isZero() {
        return 0l == value;
    }
    
    public boolean isPositive() {
        return 0l < value;
    }

    public boolean isNegative() {
        return 0l > value;
    }

    public Long setZero() {
        return value = 0l;
    }

    public Long setOne() {
        return value = 1l;
    }

    public Long setNegativeOne() {
        return value = -1l;
    }

    public Long negate() {
        if (isNull()) return null;
        return value = -value;
    }

    public Long add(Number n) {
        return value += n.longValue();
    }

    public Long subtract(Number n) {
        return value -= n.longValue();
    }

    public Long multiply(Number n) {
        return value *= n.longValue();
    }

    public Long divide(Number n) {
        return value /= n.longValue();
    }
}
