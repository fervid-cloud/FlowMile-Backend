package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service2")
public class Service2Controller {

    @GetMapping("/ping")
    public String hello() {
        return "service2 is working!!!";
    }

    @GetMapping("/read")
    public ResponseEntity<?> readService() {
        return new ResponseEntity<>("READ Successful for service2", HttpStatus.OK);
    }

    @PostMapping("/write")
    public ResponseEntity<?> writeService() {
        return new ResponseEntity<>("WRITE Successful for service2", HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editService() {
        return new ResponseEntity<>("EDIT Successful for service2", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteService() {
        return new ResponseEntity<>("DELETE Successful for service2", HttpStatus.OK);
    }
}
