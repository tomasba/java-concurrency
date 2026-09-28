package com.jep.concurrency;

import org.junit.jupiter.api.Test;

import java.util.Timer;

public class SequentialExecTest {

    @Test
    void testSequentialExecution() throws InterruptedException {
        MockUsers mockUsers = new MockUsers();
        MockOrders mockOrders = new MockOrders();

        // Execute findUsers and findOrders sequentially
        long start = System.currentTimeMillis();
        mockUsers.findUsers();
        mockOrders.findOrders();
        long end = System.currentTimeMillis();
        System.out.println("Sequential execution time: " + (end - start) + " ms");
    }

}
