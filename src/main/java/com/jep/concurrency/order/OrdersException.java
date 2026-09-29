package com.jep.concurrency.order;

public class OrdersException extends RuntimeException {
    public OrdersException(String message, Throwable cause) {
        super(message, cause);
    }
}
