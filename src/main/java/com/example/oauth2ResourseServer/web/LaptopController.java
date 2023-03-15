package com.example.oauth2ResourseServer.web;

import com.example.oauth2ResourseServer.dto.response.ApiResponse;
import com.example.oauth2ResourseServer.dto.request.LaptopRegisterDTO;
import com.example.oauth2ResourseServer.service.LaptopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/laptop")
public class LaptopController {
    private final LaptopService laptopService;

    @PostMapping("/")
    @PreAuthorize("(hasRole(('ADMIN')) and hasAuthority('CREATE')) or hasRole('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> add(
            @Valid @RequestBody LaptopRegisterDTO laptopRequestDTO
    ) {
        laptopService.add(laptopRequestDTO);
        return new ApiResponse(
                null,
                null
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ')")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<String> get(
            @PathVariable Integer id
    ) {
        return new ApiResponse(
                null,
                laptopService.get(id)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("(hasRole(('ADMIN')) and hasAuthority('CREATE')) or hasRole('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> update(
            @PathVariable Integer id,
            @Valid @RequestBody LaptopRegisterDTO laptopRequestDTO
    ) {
        return new ApiResponse(
                null,
                laptopService.update(id, laptopRequestDTO)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("(hasRole(('ADMIN')) and hasAuthority('CREATE')) or hasRole('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> delete(
            @PathVariable Integer id
    ) {
        laptopService.delete(id);
        return new ApiResponse(
                null,
                null
        );
    }


    @GetMapping("/list")
    @PreAuthorize("hasAuthority('READ')")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<String> list() {
        return new ApiResponse(
                null,
                laptopService.list()
        );
    }


}
