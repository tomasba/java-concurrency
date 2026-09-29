package com.jep.concurrency.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockUsers {

    private final Logger log = LoggerFactory.getLogger(MockUsers.class);

    User findUsers() {
        try {
            log.info("Finding users...");
            Thread.sleep(4000); // Simulate a delay in finding users
            log.info("Finished finding users...");
            return new User( 1L, "John Doe");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new UsersException("!!! Interrupted finding users... !!!", e);
        }
    }

}