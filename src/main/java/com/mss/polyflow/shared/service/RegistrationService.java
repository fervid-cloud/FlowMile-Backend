package com.mss.polyflow.shared.service;

import com.mss.polyflow.shared.dto.UserMapper;
import com.mss.polyflow.shared.dto.request.UserRegistrationDto;
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

    public Object registerNewUser(UserRegistrationDto userRegistrationDto) {

        String hashedPassword = passwordEncoder.encode(userRegistrationDto.getPassword());

        User user = User.builder()
                                     .username(userRegistrationDto.getUsername())
                                     .password(hashedPassword)
                                     .email(userRegistrationDto.getUsername())
                                     .firstName(userRegistrationDto.getFirstName())
                                     .lastName(userRegistrationDto.getLastName())
                                     .phoneNumber(userRegistrationDto.getPhoneNumber())
                                     .isVerified(false)
                                     .isEnabled(true)
                                     .isAccountLocked(false)
                                     .build();
        user = registrationRepository.save(user);

        return UserMapper.toUserDetailDto(user);
    }


    public Object verifyCurrentUser(String username) {
        return "";
    }
}
