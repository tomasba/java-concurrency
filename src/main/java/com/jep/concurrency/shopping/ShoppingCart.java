package com.jep.concurrency.shopping;

import com.jep.concurrency.order.Order;
import com.jep.concurrency.user.User;

public record ShoppingCart(User user, Order order) {
    @Override
    public String toString() {
        return "UserOrderResult{" +
                "user=" + user.id() +
                ", order=" + order.productSku() +
                '}';
    }
}