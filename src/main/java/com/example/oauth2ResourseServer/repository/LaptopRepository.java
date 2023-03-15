package com.example.oauth2ResourseServer.repository;

import com.example.oauth2ResourseServer.entity.LaptopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LaptopRepository extends JpaRepository<LaptopEntity,Integer> {
    Optional<LaptopEntity> findByName(String name);
}
