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
        /*
        By default Spring Data JPA inspects the identifier property of the given entity. If the identifier property
        is null, then the entity will be assumed as new, otherwise as not new.And so if one of your entity has an ID field not null,
        Spring will make Hibernate do an update (and so a SELECT before).
        */
        User user = User.builder()
                        .userId(null)
                        .username(userRegistrationDto.getUsername())
                        .password(hashedPassword)
                        .email(userRegistrationDto.getEmail())
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
