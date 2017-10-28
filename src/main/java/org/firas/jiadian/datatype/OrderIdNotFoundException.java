package org.firas.jiadian.datatype;

import lombok.Getter;

public class OrderIdNotFoundException
        extends OrderNotFoundException {

    public OrderIdNotFoundException(int orderId, String message) {
        super(message);
        this.orderId = orderId;
    }

    @Getter private int orderId;
}
