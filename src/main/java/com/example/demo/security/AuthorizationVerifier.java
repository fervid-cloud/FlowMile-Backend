package com.example.demo.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("authorizationVerifier")
public class AuthorizationVerifier {

    public boolean isUserAuthorizedForThisService(Authentication authentication, String authorizationInfo) {;
        System.out.println("--------------------------------------------------------------------------");
        CustomAuthenticationToken currentUserDetail = (CustomAuthenticationToken) authentication;
        System.out.println("Current user info is : " + currentUserDetail);
        System.out.println("checking for " + authorizationInfo);
        for(String currentAuthorization : currentUserDetail.getAccessInfo().getAuthorities()) {
            System.out.println("Authority of this user is : "+ currentAuthorization);
            if(currentAuthorization.equals(authorizationInfo)) {
                System.out.println("User is authorized --------------------");
                return true;
            }
        }

        System.out.println("USER IS NOT AUTHORIZED");
        return false;
    }

}
