package com.jep.concurrency;

import com.jep.concurrency.order.MockOrdersDao;
import com.jep.concurrency.user.MockUsersDao;
import org.junit.jupiter.api.Test;

public class SequentialExecTest {

    @Test
    void testSequentialExecution() throws InterruptedException {
        MockUsersDao mockUsersDao = new MockUsersDao();
        MockOrdersDao mockOrdersDao = new MockOrdersDao();

        // Execute findUsers and findOrders sequentially
        long start = System.currentTimeMillis();
        mockUsersDao.findUsers();
        mockOrdersDao.findOrders();
        long end = System.currentTimeMillis();
        System.out.println("Sequential execution time: " + (end - start) + " ms");
    }

}
