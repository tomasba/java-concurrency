package com.jep.concurrency;

public record UserOrderResult(User user, Order order) {
    @Override
    public String toString() {
        return "UserOrderResult{" +
                "user=" + user.id() +
                ", order=" + order.productSku() +
                '}';
    }
}