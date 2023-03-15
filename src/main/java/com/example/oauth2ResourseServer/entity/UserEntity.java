package com.example.oauth2ResourseServer.entity;


import com.example.oauth2ResourseServer.dto.request.UserRequestDTO;
import com.example.oauth2ResourseServer.entity.Enum.PermissionEnum;
import com.example.oauth2ResourseServer.entity.Enum.RoleEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String email;

    @NonNull
    private String password;
    @Enumerated(EnumType.STRING)
    private List<RoleEnum> roleEnumList;
    @Enumerated(EnumType.STRING)
    private List<PermissionEnum> permissionEnumList;

    public UserEntity(@NonNull String email, @NonNull String password, List<RoleEnum> roleEnumList, List<PermissionEnum> permissionEnumList) {
        this.email = email;
        this.password = password;
        this.roleEnumList = roleEnumList;
        this.permissionEnumList = permissionEnumList;
    }

    public static UserEntity of(UserRequestDTO userRequestDTO) {
        if (userRequestDTO.isUser()) {
            return UserEntity
                    .builder()
                    .email(userRequestDTO.getEmail())
                    .password(userRequestDTO.getPassword())
                    .roleEnumList(List.of(RoleEnum.USER))
                    .permissionEnumList(List.of(PermissionEnum.READ))
                    .build();
        }
        return UserEntity
                .builder()
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .roleEnumList(userRequestDTO.getRoles())
                .permissionEnumList(userRequestDTO.getPermissions())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roleEnumList.forEach((role) ->
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()))
                );
        permissionEnumList.forEach((permission) ->
                authorities.add(new SimpleGrantedAuthority(permission.name()))
                );
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
