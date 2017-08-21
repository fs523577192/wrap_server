package org.firas.common.datatype;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MutableFloat extends MutableNumber<Float> {

    public MutableFloat(Float value) {
        this.setValue(value);
    }

    public boolean isZero() {
        return 0f == value;
    }
    
    public boolean isPositive() {
        return 0f < value;
    }

    public boolean isNegative() {
        return 0f > value;
    }

    public Float setZero() {
        return value = 0f;
    }

    public Float setOne() {
        return value = 1f;
    }

    public Float setNegativeOne() {
        return value = -1f;
    }

    public Float negate() {
        if (isNull()) return null;
        return value = -value;
    }

    public Float add(Number n) {
        return value += n.floatValue();
    }

    public Float subtract(Number n) {
        return value -= n.floatValue();
    }

    public Float multiply(Number n) {
        return value *= n.floatValue();
    }

    public Float divide(Number n) {
        return value /= n.floatValue();
    }
}
