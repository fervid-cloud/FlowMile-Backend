package com.example.demo.security;


import java.util.Collection;
import javax.security.auth.Subject;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    String username;

    String credential;

    AccessInfo AccessInfo;


    public CustomAuthenticationToken(
        Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

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
    public boolean implies(Subject subject) {
        return false;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public String toString() {
        return "CustomAuthenticationToken{" +
                   "username='" + username + '\'' +
                   ", credential='" + credential + '\'' +
                   ", AccessInfo=" + AccessInfo +
                   '}';
    }
}
