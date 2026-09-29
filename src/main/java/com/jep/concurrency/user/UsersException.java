package com.jep.concurrency.user;

public class UsersException extends RuntimeException {
    public UsersException(String message, Throwable cause) {
        super(message, cause);
    }
}
