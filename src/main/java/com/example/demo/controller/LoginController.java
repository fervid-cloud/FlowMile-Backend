package com.example.demo.controller;

import com.example.demo.dto.UserLoginDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JWTManager;
import javax.security.sasl.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final JWTManager jwtManager;

    private final UserRepository userRepository;

    public LoginController(JWTManager jwtManager,
        UserRepository userRepository) {
        this.jwtManager = jwtManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    private ResponseEntity<?> loginUser(@RequestBody UserLoginDto userLoginDto)
        throws AuthenticationException {

        User user = authenticateUser(userLoginDto.getUsername(), userLoginDto.getPassword());
        if(user == null) {
            throw new AuthenticationException();
        }
        String generatedJWT = jwtManager.generateToken(user);
        return new ResponseEntity<>(generatedJWT, HttpStatus.OK);
    }

    private User authenticateUser(String username, String password) {
        if(username == null || password == null) {
            return null;
        }

        User user = userRepository.findByUserName(username);
        return user != null ? user : null;
    }

}
