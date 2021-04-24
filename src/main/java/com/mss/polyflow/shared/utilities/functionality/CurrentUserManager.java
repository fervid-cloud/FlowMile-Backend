package com.mss.polyflow.shared.utilities.functionality;

import com.mss.polyflow.shared.security.authentication.CurrentUserDetail;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserManager {

    public static boolean AUTHENTICATION_NEEDED = true;
    public static Long getCurrentUserId() {
        if (AUTHENTICATION_NEEDED) {
            CurrentUserDetail currentUserDetail = (CurrentUserDetail) SecurityContextHolder
                                                                          .getContext()
                                                                          .getAuthentication()
                                                                          .getDetails();
            return currentUserDetail.getUserId();
        } else {
            return 1l;
        }
    }
}
