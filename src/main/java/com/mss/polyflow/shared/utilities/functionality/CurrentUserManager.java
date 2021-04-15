package com.mss.polyflow.shared.utilities.functionality;

import com.mss.polyflow.shared.security.authentication.CurrentUserDetail;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserManager {
    public static Long getCurrentUserId() {
        CurrentUserDetail currentUserDetail = (CurrentUserDetail) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return currentUserDetail.getUserId();
    }
}
