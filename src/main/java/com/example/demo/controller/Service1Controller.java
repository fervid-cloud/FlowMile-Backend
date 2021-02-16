package com.example.demo.controller;

import com.example.demo.security.CustomAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service1")
public class Service1Controller {

    @GetMapping("/ping")
    public String hello() {
        return "service1 is working!!!";
    }

    @GetMapping("/read")
    @PreAuthorize("@authorizationVerifier.isUserAuthorizedForThisService(authentication, 'service1-read')")
    public ResponseEntity<?> readService() {
        return new ResponseEntity<>("READ Successful for service1", HttpStatus.OK);
    }

    @PostMapping("/write")
    @PreAuthorize("@authorizationVerifier.isUserAuthorizedForThisService(authentication, 'service1-write')")
    public ResponseEntity<?> writeService() {
        return new ResponseEntity<>("WRITE Successful for service1", HttpStatus.OK);
    }

    @PostMapping("/edit")
    @PreAuthorize("@authorizationVerifier.isUserAuthorizedForThisService(authentication, 'service1-edit')")
    public ResponseEntity<?> editService() {
        return new ResponseEntity<>("EDIT Successful for service1", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("@authorizationVerifier.isUserAuthorizedForThisService(authentication, 'service1-delete')")
    public ResponseEntity<?> deleteService() {
        System.out.println("Reached the delete service1---------------------------");
        CustomAuthenticationToken customAuthenticationToken = (CustomAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        System.out.println(customAuthenticationToken);
        return new ResponseEntity<>("DELETE Successful for service1", HttpStatus.OK);
    }

}
