package com.mss.polyflow.shared.security.authorization;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("authorizationVerifier")
public class AuthorizationVerifier {

    public boolean isUserAuthorizedForThisService(Authentication authentication, String ... authorizationInfo) {;
        System.out.println("--------------------------------------------------------------------------");
        CustomAuthenticationToken currentUserDetail = (CustomAuthenticationToken) authentication;
        System.out.println("Current user info is : " + currentUserDetail);
        System.out.println("checking for " + authorizationInfo);
        for(String userAuthorization : currentUserDetail.getAccessInfo().getAuthorities()) {
            System.out.println("Authority of this user is : "+ userAuthorization);
            for(String authorizationNeeded : authorizationInfo) {
                System.out.println("verfiy user authorization against : " + authorizationNeeded);
                if (authorizationNeeded.equals(userAuthorization)) {
                    System.out.println("User is Authorized");
                    System.out.println("user has " + authorizationNeeded + " authorization");
                    return true;
                }
            }
        }
        System.out.println("USER IS NOT AUTHORIZED");
        return false;
    }

}
