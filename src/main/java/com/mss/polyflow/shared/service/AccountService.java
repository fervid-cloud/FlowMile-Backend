package com.mss.polyflow.shared.service;

import com.mss.polyflow.shared.dto.UserMapper;
import com.mss.polyflow.shared.dto.request.ChangePasswordRequestDto;
import com.mss.polyflow.shared.dto.request.EditUserDetailDto;
import com.mss.polyflow.shared.dto.request.UserRegistrationDto;
import com.mss.polyflow.shared.dto.response.UserAuthenticatedResponseDto;
import com.mss.polyflow.shared.dto.response.UserDetailDto;
import com.mss.polyflow.shared.exception.MiscellaneousException;
import com.mss.polyflow.shared.exception.UserNotFoundException;
import com.mss.polyflow.shared.model.User;
import com.mss.polyflow.shared.repository.UserRepository;
import com.mss.polyflow.shared.security.token.JWTManager;
import com.mss.polyflow.shared.utilities.functionality.CurrentUserManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final JWTManager jwtManager;

    private final UserRepository userRepository;

    private final PasswordEncoder delegatePasswordEncoder;

    public AccountService(JWTManager jwtManager,
        UserRepository userRepository,
        PasswordEncoder delegatePasswordEncoder) {
        this.jwtManager = jwtManager;
        this.userRepository = userRepository;
        this.delegatePasswordEncoder = delegatePasswordEncoder;
    }

    public UserAuthenticatedResponseDto authenticateUser(String username, String password) {
        if (username == null || password == null) {
            throw new MiscellaneousException("Invalid data was there for login attempt");
        }

        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

//        String hashedPassword = this.delegatePasswordEncoder.encode(password);
        if (!user.getUsername().equals(username) || !this.delegatePasswordEncoder
                                                         .matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UserAuthenticatedResponseDto()
                   .setToken(jwtManager.generateToken(user));
    }

    public void changePassword(ChangePasswordRequestDto changePasswordRequestDto) {

        if (!changePasswordRequestDto.getNewPassword()
                 .equals(changePasswordRequestDto.getConfirmPassword())) {
            throw new MiscellaneousException(
                "New password mismatch found for password change request");
        }

        User user = this.userRepository.findById(CurrentUserManager.getCurrentUserId())
                        .orElseThrow(() -> new MiscellaneousException(
                            "Invalid User Request, no such user exists"));

        if (!delegatePasswordEncoder
                 .matches(changePasswordRequestDto.getCurPassword(), user.getPassword())) {
            throw new MiscellaneousException(
                "Wrong current password, password couldn't be changed");
        }

        user.setPassword(delegatePasswordEncoder.encode(changePasswordRequestDto.getNewPassword()));
        this.userRepository.save(user);

    }

    public UserDetailDto editUserDetail(EditUserDetailDto editUserDetailDto) {

        User user = this.userRepository.findById(CurrentUserManager.getCurrentUserId())
                        .orElseThrow(() -> new MiscellaneousException(
                            "Invalid User Request, no such user exists"));

        user.setFirstName(editUserDetailDto.getFirstName());
        user.setLastName(editUserDetailDto.getLastName());
        //user.setPhoneNumber(editUserDetailDto.getPhoneNumber());
        return UserMapper.toUserDetailDto(this.userRepository.save(user));
    }

    public Object getUserInfo(String username) {
        return UserMapper.toUserDetailDto(this.userRepository.findByUsername(username)
                   .orElseThrow(() -> new MiscellaneousException("No such user exists")));
    }


    public Object registerNewUser(UserRegistrationDto userRegistrationDto) {

        String hashedPassword = delegatePasswordEncoder.encode(userRegistrationDto.getPassword());
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
                        .build();
        user = userRepository.save(user);

        return UserMapper.toUserDetailDto(user);
    }
}
