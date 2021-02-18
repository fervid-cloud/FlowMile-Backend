package com.example.demo.controller;


import com.example.demo.dto.UserLoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SignUpController {


    @PostMapping("/signup")
    private ResponseEntity<?> loginUser(@RequestBody User user) {

    }

}
