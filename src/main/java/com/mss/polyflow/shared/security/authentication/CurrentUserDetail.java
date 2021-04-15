package com.mss.polyflow.shared.security.authentication;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class CurrentUserDetail {

    private Long userId;

    private String username;

    private String password;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String profileImageUrl;

    private LocalDateTime lastLoginTime;

    private LocalDateTime lastLoginTimeDisplay;

    private LocalDateTime creationTime;

    private LocalDateTime lastModifiedTime;

    private boolean isAccountLocked = false;

    private boolean isEnabled = true;

    private boolean isVerified = true;
}
