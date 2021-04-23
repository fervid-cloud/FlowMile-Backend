package com.mss.polyflow.shared.controller;


import com.mss.polyflow.shared.dto.request.UserRegistrationDto;
import com.mss.polyflow.shared.exception.InvalidRegistrationRequest;
import com.mss.polyflow.shared.service.RegistrationService;
import com.mss.polyflow.shared.utilities.response.ResponseModel;
import java.util.HashMap;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/newUser")
    public ResponseEntity<Object> registerNewUser(@RequestBody @Valid UserRegistrationDto user) {

        if(!isValidRequest(user)) {
            throw new InvalidRegistrationRequest();
        }
        return ResponseModel.sendResponse(registrationService.registerNewUser(user), "Registration successful");
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
        return ResponseModel.sendResponse(registrationService.verifyCurrentUser(username), "Verification successful");
    }

    //////////////////////////////// UTILITY METHODS

    private boolean isValidRequest(UserRegistrationDto user) {
        return user != null
                   && user.getUsername() != null && !user.getUsername().isEmpty()
                   && user.getPassword() != null && !user.getPassword().isEmpty()
                   && user.getEmail() != null && !user.getEmail().isEmpty()
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
