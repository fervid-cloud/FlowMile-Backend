package com.mss.polyflow.shared.security.authorization;


import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    private String username;

    private AccessInfo accessInfo = new AccessInfo();


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

    public AccessInfo getAccessInfo() {
        return accessInfo;
    }

    public CustomAuthenticationToken setAccessInfo(AccessInfo accessInfo) {
        this.accessInfo = accessInfo;
        return this;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }
}
