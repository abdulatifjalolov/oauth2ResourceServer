package com.example.oauth2ResourseServer.security.data;

import com.example.oauth2ResourseServer.entity.Enum.PermissionEnum;
import com.example.oauth2ResourseServer.entity.Enum.RoleEnum;
import com.example.oauth2ResourseServer.entity.UserEntity;
import com.example.oauth2ResourseServer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        //add SUPER_ADMIN when application is starting
        if (!userRepository.existsByEmail("superadmin@gmail.com")) {
            UserEntity superAdmin = UserEntity.builder()
                    .email("superadmin@gmail.com")
                    .password(passwordEncoder.encode("superadmin"))
                    .roleEnumList(Collections.singletonList(RoleEnum.SUPER_ADMIN))
                    .permissionEnumList(Arrays.asList(PermissionEnum.CREATE, PermissionEnum.READ, PermissionEnum.UPDATE, PermissionEnum.DELETE))
                    .build();
            userRepository.save(superAdmin);
        }
    }
}
