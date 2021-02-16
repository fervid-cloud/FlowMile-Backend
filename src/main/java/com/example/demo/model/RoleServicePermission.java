package com.example.demo.model;

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
public class RoleServicePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleServicePermissionId;

    private Integer roleId;

    private Integer serviceId;

    private Integer permissionId;

}
