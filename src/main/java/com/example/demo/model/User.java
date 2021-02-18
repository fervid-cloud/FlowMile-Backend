package com.example.demo.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer userId;

    @Column(nullable = false, updatable = true)
    private String username;

    private String password;

    @Column(nullable = false, updatable = true)
    private String emailId;

    private String phoneNumber;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String profileImageUrl;

    private LocalDateTime lastLoginTime;

    private LocalDateTime lastLoginTimeDisplay;

    private LocalDateTime creationTime;

    private LocalDateTime lastModifiedTime;

    private boolean isAccountLocked;

    private boolean isEnabled;

    private boolean isVerified;

}
