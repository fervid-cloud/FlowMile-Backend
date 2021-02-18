package com.example.demo.controller;


import com.example.demo.dto.RegistrationRequestDto;
import com.example.demo.dto.ResponseModel;
import com.example.demo.exception.InvalidRegistrationRequest;
import com.example.demo.service.RegistrationService;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistrationController {


    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/new")
    public ResponseEntity<Object> registerNewUser(@RequestBody RegistrationRequestDto user) {

        if(!isValidRequest(user)) {
            throw new InvalidRegistrationRequest();
        }
        return sendResponse(registrationService.registerNewUser(user));
    }


    @PostMapping("/verifyUser")
    public ResponseEntity<Object> verifyCurrentUser(@RequestParam("username") String username) {
        if(username == null) {
            throw new InvalidRegistrationRequest();
        }

        // 1. user get registered, in database, the column of new user with verified is set to false
        // 2. now front-end calls the verifyUser api, to send the mail to the user for verification
        // 3. once backend gets the request, it creates a particular verification type jwt, so any server
        // can verify it making things stateless and thus scalable as load balancer can redirect to any server
        // 4. now backend through smtp and mail server, sends the request to the user for verification with a generated front-end link having jwt in it's querystring or path parameters/variable
        // 5. now when user opens the mail and click the link, that page javascript code does the following, it calls an api which verifies the newUserVerificationJwt
        // and then if the jwt is verified, it marks the user as verified in the database and sends the request as successfull to that page script(that user opened) else
        // if for some reason jwt fails(for example expiration or server issue), that front-link page tells the user that verification has failed,
        // so either he tries to do the verification with the same link after some time or request to generate a new one
        // if he goes for generating a new link, then same cycle will follow
        return sendResponse(registrationService.verifyCurrentUser(username));
    }

    //////////////////////////////// UTILITY METHODS

    private ResponseEntity<Object> sendResponse(Object response) {
        ResponseModel responseModel = ResponseModel.builder()
                                          .successStatus(1)
                                          .message("successful")
                                          .object(response)
                                          .build();

        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    private boolean isValidRequest(RegistrationRequestDto user) {
        return user != null
                   && user.getUsername() != null && !user.getUsername().isEmpty()
                   && user.getPassword() != null && !user.getPassword().isEmpty()
                   && user.getEmailId() != null && !user.getEmailId().isEmpty()
                   && user.getFirstName() != null && !user.getFirstName().isEmpty()
                   && user.getLastName() != null && !user.getLastName().isEmpty();
    }

    @GetMapping("/ping")
    private ResponseEntity<?> pingCheck() {
        return new ResponseEntity<>(new HashMap() {
            {
                put("response", "registration end point manager working fine");
                put("status", HttpStatus.OK.value());
            }
        }, HttpStatus.OK);
    }

}
