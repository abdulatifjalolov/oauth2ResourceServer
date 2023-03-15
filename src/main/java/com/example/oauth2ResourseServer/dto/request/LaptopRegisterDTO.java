package com.example.oauth2ResourseServer.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LaptopRegisterDTO {
    @NotBlank(message = "Name is required")
    private String name;
    private double price;
    private short RAM;
    private String CPU;
}
