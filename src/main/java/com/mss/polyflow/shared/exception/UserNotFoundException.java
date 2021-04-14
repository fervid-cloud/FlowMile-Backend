package com.mss.polyflow.shared.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String name = "UserNotFoundException";

    private static final String message = "No Such User Exists";

    public static String getName() {
        return name;
    }

    @Override
    public String getMessage() {
        return message;
    }
}