package com.mss.polyflow.shared.security.authorization;

import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component("authorizationVerifier")
public class AuthorizationVerifier {

    public boolean isUserAuthorizedForThisService(Authentication authentication, String ... authorizationInfo) {;
        log.info("starting user authorization process");

        CustomAuthenticationToken currentUserDetail = (CustomAuthenticationToken) authentication;
        List<String> authorities = currentUserDetail.getAccessInfo().getAuthorities();
        authorities.sort((x, y) -> x.compareTo(y));
        for(String authorizationNeeded : authorizationInfo) {
            if (Collections.binarySearch(authorities, authorizationNeeded) < 0) {
                log.info("user doesn't have all the permissions required for this service, user is not authorized");
                return false;
            }
        }

        log.info("user has all the permissions required for this service, user is authorized");
        return true;
    }

}
