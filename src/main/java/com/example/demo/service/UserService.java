package com.example.demo.service;

import com.example.demo.repository.UserRepository;
import com.example.demo.security.AccessInfo;
import java.util.Arrays;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AccessInfo getAccessInfo(String username) {
        AccessInfo accessInfo = new AccessInfo();
        if(username.equals("user1@test.com")) {
            accessInfo.setRoleId(2);
            accessInfo.setRoleName("admin");
            accessInfo.setAuthorities(Arrays.asList(new String[]{"service1-read", "service1-write"}));
        } else if(username.equals("user2@test.com")) {
            accessInfo.setRoleId(1);
            accessInfo.setRoleName("superadmin");
            accessInfo.setAuthorities(Arrays.asList(new String[]{"service1-read", "service1-write", "service1-edit", "service1-delete"}));
        } else {
            return null;
        }
        return accessInfo;
    }
}
