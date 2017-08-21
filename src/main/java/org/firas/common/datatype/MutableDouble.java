package org.firas.common.datatype;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MutableDouble extends MutableNumber<Double> {

    public MutableDouble(Double value) {
        this.setValue(value);
    }

    public boolean isZero() {
        return 0. == value;
    }
    
    public boolean isPositive() {
        return 0. < value;
    }

    public boolean isNegative() {
        return 0. > value;
    }

    public Double setZero() {
        return value = 0.;
    }

    public Double setOne() {
        return value = 1.;
    }

    public Double setNegativeOne() {
        return value = -1.;
    }

    public Double negate() {
        if (isNull()) return null;
        return value = -value;
    }

    public Double add(Number n) {
        return value += n.doubleValue();
    }

    public Double subtract(Number n) {
        return value -= n.doubleValue();
    }

    public Double multiply(Number n) {
        return value *= n.doubleValue();
    }

    public Double divide(Number n) {
        return value /= n.doubleValue();
    }
}
