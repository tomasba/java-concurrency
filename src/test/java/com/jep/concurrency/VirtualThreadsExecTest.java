package com.jep.concurrency;

import com.jep.concurrency.order.MockOrders;
import com.jep.concurrency.order.Order;
import com.jep.concurrency.shopping.ShoppingCart;
import com.jep.concurrency.user.MockUsers;
import com.jep.concurrency.user.User;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class VirtualThreadsExecTest {

    @Test
    void shouldExecuteInVirtualThreads() throws InterruptedException, ExecutionException {
        MockUsers mockUsers = new MockUsers();
        MockOrders mockOrders = new MockOrders();

        // Execute findUsers and findOrders using virtual threads
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            System.out.println("Starting virtual threads execution...");
            long start = System.currentTimeMillis();

            // Both calls start here immediately, in parallel, each on its own virtual thread.
            System.out.println("Submitted tasks to virtual threads executor. Waiting executors to complete...");
            Future<User> user = executor.submit(mockUsers::findUsers);
            Future<Order> order = executor.submit(mockOrders::findOrders);

            // Wait for both tasks to complete
            var result = new ShoppingCart(user.get(), order.get());
            System.out.println("Executors finished !!! Result ready: " + result);

            long end = System.currentTimeMillis();
            System.out.println("Virtual threads execution time: " + (end - start) + " ms");
        }
    }

    /**
     * mockOrders::findOrdersFailing implicitly fails in 1 second. The mockUsers::findUsers takes 4 seconds to complete.
     * despite the failure of the findOrdersFailing, the findUsers will continue to execute and complete. The
     * mockOrders::findOrdersFailing exception will be seen only when longest task is completed.
     *
     * It is very inefficient to wait for the longest task to complete before seeing the exception of the failing task.
     *
     */
    @Test
    void shouldExecuteInVirtualThreadsWithFailure() throws InterruptedException, ExecutionException {
        MockUsers mockUsers = new MockUsers();
        MockOrders mockOrders = new MockOrders();

        // Execute findUsers and findOrders using virtual threads
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            System.out.println("Starting virtual threads execution...");
            long start = System.currentTimeMillis();

            // Both calls start here immediately, in parallel, each on its own virtual thread.
            System.out.println("Submitted tasks to virtual threads executor. Waiting Futures to complete...");
            Future<User> user = executor.submit(mockUsers::findUsers);
            Future<Order> order = executor.submit(mockOrders::findOrdersFailing);

            // Wait for both tasks to complete. the get() calls are blocking ones.
            var result = new ShoppingCart(user.get(), order.get());
            System.out.println("Executors finished !!! Result ready: " + result);

            long end = System.currentTimeMillis();
            System.out.println("Virtual threads execution time: " + (end - start) + " ms");
        }
    }

}
