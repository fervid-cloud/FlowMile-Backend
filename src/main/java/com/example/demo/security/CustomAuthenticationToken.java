package com.example.demo.security;


import java.util.Collection;
import javax.security.auth.Subject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class CustomAuthenticationToken implements Authentication {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }
}
