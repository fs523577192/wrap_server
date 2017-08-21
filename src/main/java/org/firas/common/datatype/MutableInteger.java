package org.firas.common.datatype;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MutableInteger extends MutableNumber<Integer> {

    public MutableInteger(Integer value) {
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

    public Integer setZero() {
        return value = 0;
    }

    public Integer setOne() {
        return value = 1;
    }

    public Integer setNegativeOne() {
        return value = -1;
    }

    public Integer negate() {
        if (isNull()) return null;
        return value = -value;
    }

    public Integer add(Number n) {
        return value += n.intValue();
    }

    public Integer subtract(Number n) {
        return value -= n.intValue();
    }

    public Integer multiply(Number n) {
        return value *= n.intValue();
    }

    public Integer divide(Number n) {
        return value /= n.intValue();
    }
}
