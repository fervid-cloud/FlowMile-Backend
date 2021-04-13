package com.mss.polyflow.shared.exception;

public class UnVerifiedUserLoginException extends RuntimeException{

    private static final String name = "UnVerifiedUserLoginException";

    private static final String message = "You are unverified, please verify yourself first";

    UnVerifiedUserLoginException() {
        super(message);
    }

    public String getName() {
        return name;
    }

}
