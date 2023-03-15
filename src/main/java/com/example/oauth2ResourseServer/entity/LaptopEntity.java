package com.example.oauth2ResourseServer.entity;

import com.example.oauth2ResourseServer.dto.request.LaptopRegisterDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LaptopEntity extends BaseEntity {
    @Column(unique = true,nullable = false)
    @NotBlank(message = "Name is required")
    private String name;

    private double price;

    private int RAM;

    private String CPU;


    public static LaptopEntity of(LaptopRegisterDTO laptopRequestDTO) {
        return LaptopEntity
                .builder()
                .CPU(laptopRequestDTO.getCPU())
                .RAM(laptopRequestDTO.getRAM())
                .name(laptopRequestDTO.getName())
                .price(laptopRequestDTO.getPrice())
                .build();
    }
}
