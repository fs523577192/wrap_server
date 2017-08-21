package org.firas.common.datatype;

public abstract class MutableNumber<T extends Number> extends Mutable<T> {

    public abstract boolean isZero();
    
    public abstract boolean isPositive();

    public abstract boolean isNegative();

    public abstract T setZero();
    
    public abstract T setOne();

    public abstract T setNegativeOne();

    public abstract T negate();

    public abstract T add(Number n);

    public abstract T subtract(Number n);

    public abstract T multiply(Number n);

    public abstract T divide(Number n);
}
