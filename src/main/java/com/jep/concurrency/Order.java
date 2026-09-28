package com.jep.concurrency;

public record Order(Long orderId, String productSku, int quantity) {
}
