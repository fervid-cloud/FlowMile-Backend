package com.mss.polyflow.shared.controller;

import com.mss.polyflow.shared.dto.LoginResponseDto;
import com.mss.polyflow.shared.dto.UserLoginDto;
import com.mss.polyflow.shared.model.User;
import com.mss.polyflow.shared.repository.UserRepository;
import com.mss.polyflow.shared.security.JWTManager;
import javax.security.sasl.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
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
            throw new BadCredentialsException("Invalid username or password");
        }
        String generatedJWT = jwtManager.generateToken(user);
        return new ResponseEntity<>(new LoginResponseDto().setJwtToken(generatedJWT).setUsername(userLoginDto.getUsername()).setMessage("Login Successful"), HttpStatus.OK);
    }

    private User authenticateUser(String username, String password) {
        if(username == null || password == null) {
            System.out.println("userdetail is not correct#############################");
            return null;
        }

        User user = userRepository.findByUserName(username);
        if(user == null) {
            user = User.builder().username(username).password(password).build();
        }
        return user;
    }
}
