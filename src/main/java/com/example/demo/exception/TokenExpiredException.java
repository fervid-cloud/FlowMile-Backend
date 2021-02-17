package com.example.demo.exception;

public class TokenExpiredException extends RuntimeException{

    private static final String message = "provided token is expired, can't authenticate the user";

    public TokenExpiredException() {
        super(message);
    }

}
