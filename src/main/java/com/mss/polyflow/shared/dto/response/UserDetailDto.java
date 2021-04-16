package com.mss.polyflow.shared.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserDetailDto {

    private Long userId;

    private String username;

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
