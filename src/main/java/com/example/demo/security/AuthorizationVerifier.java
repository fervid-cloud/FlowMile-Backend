package com.example.demo.security;

import org.springframework.stereotype.Component;

@Component("authorizationVerifier")
public class AuthorizationVerifier {

    public boolean hasThisServiceAuthorization(CustomAuthenticationToken authentication, String authorizationInfo) {

        for(String currentAuthorization : authentication.getAccessInfo().getAuthorities()) {
            if(currentAuthorization.equals(authorizationInfo)) {
                return true;
            }
        }
        return false;
    }

}
