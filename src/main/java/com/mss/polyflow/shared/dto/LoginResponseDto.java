package com.mss.polyflow.shared.dto;

public class LoginResponseDto {

    private String username;

    private String jwtToken;

    private String message;

    public String getJwtToken() {
        return jwtToken;
    }

    public LoginResponseDto setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public LoginResponseDto setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public LoginResponseDto setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public String toString() {
        return "LoginResponseDto{" +
                   "username='" + username + '\'' +
                   ", jwtToken='" + jwtToken + '\'' +
                   ", message='" + message + '\'' +
                   '}';
    }
}
