package com.jep.concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockOrders {

    private final Logger log = LoggerFactory.getLogger(MockOrders.class);

    Order findOrders() {
        try {
            log.info("Finding orders...");
            Thread.sleep(6000); // Simulate a delay in finding orders
            log.info("Finished finding orders...");
            return new Order( 10L, "ABD123454", 1);
        } catch (InterruptedException e) {
            log.warn("!!! Interrupted finding orders... !!!");
            throw new RuntimeException(e);
        }
    }

    Order findOrdersFailing() {
        try {
            log.info("Finding orders...");
            Thread.sleep(1000); // Simulate a delay in finding orders
            log.info("Finished finding orders with FAILURE...");
            throw new IllegalArgumentException("Simulated exception in findOrdersFailing");
        } catch (InterruptedException e) {
            log.warn("!!! Interrupted finding orders... !!!");
            throw new RuntimeException(e);
        }
    }

}
