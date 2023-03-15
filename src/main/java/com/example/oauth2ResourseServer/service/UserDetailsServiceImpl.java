package com.example.oauth2ResourseServer.service;

import com.example.oauth2ResourseServer.entity.Enum.RoleEnum;
import com.example.oauth2ResourseServer.entity.UserEntity;
import com.example.oauth2ResourseServer.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService, AuditorAware<Integer> {

    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;


    public void createUser(UserEntity userEntity) {
        Optional<UserEntity> optionalUserEntity =
                userRepository.findByEmail(userEntity.getEmail());
        if (optionalUserEntity.isPresent())
            throw new IllegalArgumentException(String.format("email %s already exist", userEntity.getEmail()));
        if (isUserSuperAdmin(userEntity))
            throw new IllegalArgumentException("user role cannot be SUPER_ADMIN or ADMIN");
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
    }

    private boolean isUserSuperAdmin(UserEntity userEntity) {
        return userEntity
                .getRoleEnumList().stream()
                .anyMatch((e) -> {
                    return e.name().equals(RoleEnum.SUPER_ADMIN.name());
                });
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        MessageFormat.format("email {0} not found", email)
                ));
    }

    @Override
    public @NonNull Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof UserEntity principal))
            return Optional.empty();
        return Optional.of(principal.getId());
    }
}
