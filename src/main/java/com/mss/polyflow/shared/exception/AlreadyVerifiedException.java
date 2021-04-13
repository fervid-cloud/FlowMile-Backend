package com.mss.polyflow.shared.exception;

public class AlreadyVerifiedException extends RuntimeException{

    private static final String name = "AlreadyVerifiedException";

    private static final String message = "You are already Verified";

    AlreadyVerifiedException() {
        super(message);
    }

    public String getName() {
        return name;
    }

}
