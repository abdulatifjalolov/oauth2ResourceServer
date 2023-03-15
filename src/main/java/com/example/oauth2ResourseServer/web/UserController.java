package com.example.oauth2ResourseServer.web;

import com.example.oauth2ResourseServer.entity.UserEntity;
import com.example.oauth2ResourseServer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/{id}")
    @PreAuthorize("#userRequestDTO.id == #id")
    public ResponseEntity user(
            @AuthenticationPrincipal UserEntity userRequestDTO,
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(userRepository.findById(id).orElseThrow());
    }
}