package com.jep.concurrency.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockOrdersDao {

    private final Logger log = LoggerFactory.getLogger(MockOrdersDao.class);

    public Order findOrders() {
        try {
            log.info("Finding orders...");
            Thread.sleep(6000); // Simulate a delay in finding orders
            log.info("Finished finding orders...");
            return new Order( 10L, "ABD123454", 1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new OrdersException("!!! Interrupted finding orders... !!!", e);
        }
    }

    public Order findOrdersFailing() {
        try {
            log.info("Finding orders...");
            Thread.sleep(1000); // Simulate a delay in finding orders
            log.info("Finished finding orders with FAILURE...");
            throw new IllegalArgumentException("Simulated exception in findOrdersFailing");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new OrdersException("!!! Interrupted finding orders... !!!", e);
        }
    }

}
