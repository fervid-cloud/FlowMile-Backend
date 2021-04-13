package com.mss.polyflow.shared.service;

import com.mss.polyflow.shared.dto.RegistrationRequestDto;
import com.mss.polyflow.shared.model.User;
import com.mss.polyflow.shared.repository.RegistrationRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;

    private final PasswordEncoder passwordEncoder;

    public RegistrationService(
        RegistrationRepository registrationRepository,
        PasswordEncoder delegatingPasswordEncoder) {
        this.registrationRepository = registrationRepository;
        this.passwordEncoder = delegatingPasswordEncoder;
    }

    public Object registerNewUser(RegistrationRequestDto registrationRequestDto) {

        String hashedPassword = getPlainToHashedPassword(registrationRequestDto.getPassword());

        User newRegisteredUser = User.builder()
                                     .username(registrationRequestDto.getUsername())
                                     .password(hashedPassword)
                                     .emailId(registrationRequestDto.getUsername())
                                     .firstName(registrationRequestDto.getFirstName())
                                     .lastName(registrationRequestDto.getLastName())
                                     .phoneNumber(registrationRequestDto.getPhoneNumber())
                                     .isVerified(false)
                                     .isEnabled(true)
                                     .isAccountLocked(false)
                                     .build();
        registrationRepository.save(newRegisteredUser);
        return registrationRequestDto;

    }

    private String getPlainToHashedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public Object verifyCurrentUser(String username) {
        return "";
    }
}
