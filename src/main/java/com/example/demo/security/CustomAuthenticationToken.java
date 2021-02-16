package com.example.demo.security;


public class CustomAuthenticationToken{

    String username;

    String credential;

    AccessInfo AccessInfo;

    public String getUsername() {
        return username;
    }

    public CustomAuthenticationToken setUsername(String username) {
        this.username = username;
        return this;
    }


    public com.example.demo.security.AccessInfo getAccessInfo() {
        return AccessInfo;
    }

    public CustomAuthenticationToken setAccessInfo(
        com.example.demo.security.AccessInfo accessInfo) {
        AccessInfo = accessInfo;
        return this;
    }

    public String getCredential() {
        return credential;
    }

    public CustomAuthenticationToken setCredential(String credential) {
        this.credential = credential;
        return this;
    }
}
