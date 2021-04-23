package com.mss.polyflow.shared.controller;

import com.mss.polyflow.shared.dto.request.ChangePasswordRequestDto;
import com.mss.polyflow.shared.dto.request.EditUserDetailDto;
import com.mss.polyflow.shared.dto.request.UserLoginDto;
import com.mss.polyflow.shared.service.AccountService;
import com.mss.polyflow.shared.utilities.response.ResponseModel;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/account")
public class AccountController {


    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/login")
    private ResponseEntity<?> loginUser(@RequestBody @Valid UserLoginDto userLoginDto) {

        Object authenticationResponse= accountService.authenticateUser(userLoginDto.getUsername(), userLoginDto.getPassword());
        return ResponseModel.sendResponse(authenticationResponse, "Login Successful");
    }



    @PostMapping("/changePassword")
    private ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordRequestDto changePasswordRequestDto) {
        this.accountService.changePassword(changePasswordRequestDto);
        return ResponseModel.sendResponse(null, "password changed successfully");

    }


    @PostMapping("/editUserDetail")
    private ResponseEntity<?> editUserDetail(@RequestBody @Valid EditUserDetailDto editUserDetailDto) {

        return ResponseModel.sendResponse(this.accountService.editUserDetail(editUserDetailDto), "user details updated successfully");

    }

    @GetMapping("/userInfo/{username}")
    private ResponseEntity<?> getUserInfo(@PathVariable String username) {
        return ResponseModel.sendResponse(this.accountService.getUserInfo(username), "User info fetched successfully");
    }







}
