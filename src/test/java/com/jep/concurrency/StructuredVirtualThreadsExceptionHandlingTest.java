package com.jep.concurrency;

import com.jep.concurrency.order.MockOrdersDao;
import com.jep.concurrency.order.Order;
import com.jep.concurrency.shopping.ShoppingCart;
import com.jep.concurrency.user.MockUsersDao;
import com.jep.concurrency.user.User;
import org.junit.jupiter.api.Test;

import java.util.concurrent.StructuredTaskScope;

public class StructuredVirtualThreadsExceptionHandlingTest {

    @Test
    void shouldExecuteStructuredVirtualThreads() throws InterruptedException {
        MockUsersDao mockUsersDao = new MockUsersDao();
        MockOrdersDao mockOrdersDao = new MockOrdersDao();

        long start = System.currentTimeMillis();

        // JDK25 preview mode. Some slight changes are in JDK27. Should be final in JDK28.

        // Default policy: wait for all subtasks to succeed or fail as soon as one fails.
        // close() runs at the end of the block whether we returned or threw,
        // and does not return until both threads are done.
        try (var scope = StructuredTaskScope.open()) {
            System.out.println("Submitted tasks to virtual threads executor. Waiting Subtasks to complete...");
            // with the fork.. subtasks are executed immediately in parallel, each on its own virtual thread.
            StructuredTaskScope.Subtask<User> userSubtask = scope.fork(mockUsersDao::findUsers);
            StructuredTaskScope.Subtask<Order> orderSubtask = scope.fork(mockOrdersDao::findOrders);

//            System.out.println("Implicit 6s wait...");
//            Thread.sleep(6000);
            System.out.println("Waiting for subtasks to complete...");

            // One wait for the whole group. If either subtask fails, the other
            // is interrupted, and the failure comes out of here as an ExecutionException.
            scope.join();           // Join both tasks

            // join() returned, so both succeeded and get() cannot fail.
            var result = new ShoppingCart(userSubtask.get(), orderSubtask.get());
            System.out.println("Structured virtual threads finished !!! Result ready: " + result);
        }
        long end = System.currentTimeMillis();
        System.out.println("Structured virtual threads execution time: " + (end - start) + " ms");
    }

    @Test
    void shouldExecuteStructuredVirtualThreadsWithFailingSubtask() throws InterruptedException {
        MockUsersDao mockUsersDao = new MockUsersDao();
        MockOrdersDao mockOrdersDao = new MockOrdersDao();

        long start = System.currentTimeMillis();

        // JDK25 preview mode. Some slight changes are in JDK27. Should be final in JDK28.

        // Default policy: wait for all subtasks to succeed or fail as soon as one fails.
        // close() runs at the end of the block whether we returned or threw,
        // and does not return until both threads are done.
        try (var scope = StructuredTaskScope.open()) {
            System.out.println("Submitted tasks to virtual threads executor. Waiting Subtasks to complete...");
            StructuredTaskScope.Subtask<User> userSubtask = scope.fork(mockUsersDao::findUsers);
            StructuredTaskScope.Subtask<Order> orderSubtask = scope.fork(mockOrdersDao::findOrdersFailing);
            System.out.println("Waiting for subtasks to complete...");
            scope.join();           // Join both tasks

            var result = new ShoppingCart(userSubtask.get(), orderSubtask.get());
            System.out.println("Structured virtual threads finished !!! Result ready: " + result);
        }
        long end = System.currentTimeMillis();
        System.out.println("Structured virtual threads execution time: " + (end - start) + " ms");
    }
}
