package com.example.demo.security;

import org.springframework.stereotype.Component;

@Component("authorityChecker")
public class AuthorityChecker {

    public boolean isThisUserAllowed(CustomAuthenticationToken authentication) {
        return true;
    }


}
