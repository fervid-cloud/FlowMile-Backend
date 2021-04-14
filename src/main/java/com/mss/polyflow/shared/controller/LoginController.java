package com.mss.polyflow.shared.controller;

import com.mss.polyflow.shared.dto.request.UserLoginDto;
import com.mss.polyflow.shared.dto.response.UserAuthenticatedResponseDto;
import com.mss.polyflow.shared.exception.MiscellaneousException;
import com.mss.polyflow.shared.exception.UserNotFoundException;
import com.mss.polyflow.shared.model.User;
import com.mss.polyflow.shared.repository.UserRepository;
import com.mss.polyflow.shared.security.token.JWTManager;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final JWTManager jwtManager;

    private final UserRepository userRepository;

    private final PasswordEncoder delegatePasswordEncoder;

    public LoginController(JWTManager jwtManager,
        UserRepository userRepository,
        PasswordEncoder delegatePasswordEncoder) {
        this.jwtManager = jwtManager;
        this.userRepository = userRepository;
        this.delegatePasswordEncoder = delegatePasswordEncoder;
    }

    @PostMapping("/login")
    private ResponseEntity<?> loginUser(@RequestBody @Valid UserLoginDto userLoginDto) {

        User user = authenticateUser(userLoginDto.getUsername(), userLoginDto.getPassword());
        String generatedJWT = jwtManager.generateToken(user);
        return new ResponseEntity<>(new UserAuthenticatedResponseDto().setAccessToken(generatedJWT).setUsername(userLoginDto.getUsername()).setMessage("Login Successful"), HttpStatus.OK);
    }

    private User authenticateUser(String username, String password) {
        if(username == null || password == null) {
            throw new MiscellaneousException("Bad Request was there while attempting to LogIn");
        }

        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException :: new);

        String hashedPassword = this.delegatePasswordEncoder.encode(password);
        if(!user.getUsername().equals(username) || !user.getPassword().equals(hashedPassword)) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return user;
    }
}
