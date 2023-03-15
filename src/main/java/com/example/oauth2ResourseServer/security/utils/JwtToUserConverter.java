package com.example.oauth2ResourseServer.security.utils;

import com.example.oauth2ResourseServer.entity.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
        UserEntity user = new UserEntity();
        user.setId(Integer.valueOf(jwt.getSubject()));

        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Map<String, String>> authoritiesList = jwt.getClaim("authorities");

        authoritiesList.forEach((e) -> {
            e.values().forEach((authority) -> {
                authorities.add(new SimpleGrantedAuthority(authority));
            });
        });

        return new UsernamePasswordAuthenticationToken(
                user, jwt, authorities
        );
    }
}
