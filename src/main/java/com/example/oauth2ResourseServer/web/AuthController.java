package com.example.oauth2ResourseServer.web;


import com.example.oauth2ResourseServer.dto.request.UserRequestDTO;
import com.example.oauth2ResourseServer.dto.response.TokenDTO;
import com.example.oauth2ResourseServer.entity.UserEntity;
import com.example.oauth2ResourseServer.security.utils.TokenGenerator;
import com.example.oauth2ResourseServer.service.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    private final TokenGenerator tokenGenerator;

    private final DaoAuthenticationProvider daoAuthenticationProvider;

    private final JwtAuthenticationProvider refreshTokenAuthProvider;

    public AuthController(UserDetailsServiceImpl userDetailsServiceImpl,
                          TokenGenerator tokenGenerator,
                          DaoAuthenticationProvider daoAuthenticationProvider,
                          @Qualifier("jwtRefreshTokenAuthProvider") JwtAuthenticationProvider refreshTokenAuthProvider) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.tokenGenerator = tokenGenerator;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.refreshTokenAuthProvider = refreshTokenAuthProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody UserRequestDTO userRequestDTO
    ) {
        UserEntity userEntity = UserEntity.of(userRequestDTO);
        userDetailsServiceImpl.createUser(userEntity);
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(userEntity, userRequestDTO.getPassword(), userEntity.getAuthorities());
        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

    @PostMapping("/login")
    public ResponseEntity login(
            @Valid @RequestBody UserRequestDTO userRequestDTO
    ) {
        UserEntity userEntity = UserEntity.of(userRequestDTO);
        Authentication authentication = daoAuthenticationProvider.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(userRequestDTO.getEmail(), userRequestDTO.getPassword()));
        return ok(tokenGenerator.createToken(authentication));
    }

    @PostMapping("/token")
    public ResponseEntity<?> token(
            @RequestBody TokenDTO tokenDTO
    ) {
        Authentication authentication =
                refreshTokenAuthProvider.authenticate(new BearerTokenAuthenticationToken(tokenDTO.getRefreshToken()));
//        Jwt jwt = (Jwt) authentication.getCredentials();
//         check if present in db and not revoked, etc
        return ok(tokenGenerator.createToken(authentication));
    }
}
