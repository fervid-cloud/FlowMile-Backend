package com.example.demo.utilities;

import com.example.demo.model.Permission;
import com.example.demo.model.Role;
import com.example.demo.model.RoleServicePermission;
import com.example.demo.model.Service;
import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.RoleServicePermissionRepository;
import com.example.demo.repository.ServiceRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SampleDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final ServiceRepository serviceRepository;

    private final PermissionRepository permissionRepository;

    private final UserRoleRepository userRoleRepository;

    private final RoleServicePermissionRepository roleServicePermissionRepository;

    public SampleDataInitializer(UserRepository userRepository,
        RoleRepository roleRepository,
        ServiceRepository serviceRepository,
        PermissionRepository permissionRepository,
        UserRoleRepository userRoleRepository,
        RoleServicePermissionRepository roleServicePermissionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.serviceRepository = serviceRepository;
        this.permissionRepository = permissionRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleServicePermissionRepository = roleServicePermissionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        int len = args.length;
        log.info("-----------------------------------------------");
        log.info("printing the argument data of commandLineRunner");
        for(int i = 0; i < len; ++i) {
            System.out.println(args[i]);
        }
        log.info("end of printing of command line runner");
        log.info("------------------------------------------------");
        initializeUsers();
        initializeRoles();
        initializeServices();
        initializePermissions();
        initializeUserRoles();
        initializeRoleServicePermissions();
    }

    private void initializeRoleServicePermissions() {
        Integer [][] roleServicePermissionMappings = new Integer [][] {{2, 1, 1}, {2, 1, 2}, {1, 1, 1}, {1, 1, 2}, {1, 1, 3}, {1, 1, 4}};
        roleServicePermissionRepository.saveAll(Arrays.stream(roleServicePermissionMappings)
        .map(roleServicePermissionMapping -> {
            return RoleServicePermission.builder()
                .roleId(roleServicePermissionMapping[0])
                .serviceId(roleServicePermissionMapping[1])
                .permissionId(roleServicePermissionMapping[2])
                .endPointId(-1)
                .build();
        })
        .collect(Collectors.toList()));
    }

    private void initializeUserRoles() {
        Integer [][] userRoleMappings = new Integer[][] { {1, 2}, {2,3}, {10,1} };
        userRoleRepository.saveAll(Arrays.stream(userRoleMappings).map(userRoleMapping -> {
            return UserRole.builder()
                       .userId(userRoleMapping[0])
                       .roleId(userRoleMapping[1])
                       .build();
        })
        .collect(Collectors.toList()));
    }

    private void initializePermissions() {
        String [] samplePermissions = new String [] {"read", "write", "edit", "delete"};
        permissionRepository.saveAll(Arrays.stream(samplePermissions).map(permissionName -> {
            return Permission.builder()
                .permissionName(permissionName)
                .build();
        })
        .collect(Collectors.toList()));
    }

    private void initializeServices() {
        String [] sampleServices = new String [] {"service1", "service2", "service3", "service4", "service5"};
        serviceRepository.saveAll(
          Arrays.stream(sampleServices).map(serviceName -> {
              return Service.builder()
                  .serviceName(serviceName)
                  .build();
          })
          .collect(Collectors.toList()));
    }

    private void initializeRoles() {
        String [] sampleRoles = new String [] {"superadmin", "admin", "level3", "level2", "level1"};
        int n = sampleRoles.length;
        roleRepository.saveAll(Arrays.stream(sampleRoles).map(roleName -> {
            return Role.builder()
                .roleName(roleName)
                .build();
        })
        .collect(Collectors.toList()));

    }

    private void initializeUsers() {
        int n = 10;
        for(int i = 1 ; i <= n; ++i) {
            userRepository.save(User.builder()
            .userId(i)
            .username("user" + i + "@test.com")
            .password("user" + i + "123")
            .emailId("user" + i + "@test.com")
            .firstName("firstName" + i)
            .lastName("lastName" + i)
            .phoneNumber("123456789" + (i - 1))
            .build());
        }
    }
}
