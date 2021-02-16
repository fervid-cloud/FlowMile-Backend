package com.example.demo.controller;

import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class UserController {

        @GetMapping("/firstEndPoint")
        public String hello() {
            return "first user allowed!";
        }

        @GetMapping("/secondEndPoint")
        public String write() {
            return "written";
        }

        @GetMapping("/ping/**")
        public ResponseEntity<?> serverPing() {
            System.out.println("Ping request came, responding the ping request");
            return new ResponseEntity(new HashMap<String, String>() {
                {
                    put("serverStatus", "working fine");
                }
            }, HttpStatus.OK);
        }
}
