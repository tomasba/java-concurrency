package com.jep.concurrency;

import com.jep.concurrency.order.MockOrders;
import com.jep.concurrency.user.MockUsers;
import org.junit.jupiter.api.Test;

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
