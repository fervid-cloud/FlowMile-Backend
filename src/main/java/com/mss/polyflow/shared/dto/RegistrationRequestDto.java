package com.mss.polyflow.shared.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RegistrationRequestDto {

    private String username;

    private String password;

    private String emailId;

    private String phoneNumber;

    private String firstName;

    private String lastName;
}
