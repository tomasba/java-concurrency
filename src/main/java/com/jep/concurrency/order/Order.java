package com.jep.concurrency.order;

public record Order(Long orderId, String productSku, int quantity) {
}
