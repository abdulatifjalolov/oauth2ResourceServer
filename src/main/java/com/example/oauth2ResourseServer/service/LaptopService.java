package com.example.oauth2ResourseServer.service;

import com.example.oauth2ResourseServer.common.exception.RecordNotFountException;
import com.example.oauth2ResourseServer.dto.request.LaptopRegisterDTO;
import com.example.oauth2ResourseServer.entity.LaptopEntity;
import com.example.oauth2ResourseServer.repository.LaptopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LaptopService implements BaseService<LaptopRegisterDTO, LaptopEntity> {
    private final LaptopRepository laptopRepository;

    @Override
    public List<LaptopEntity> list() {
        List<LaptopEntity> laptopEntities = laptopRepository.findAll();
        if (laptopEntities.isEmpty())
            throw new NullPointerException("empty list");
        return laptopEntities;
    }

    @Override
    public LaptopEntity get(Integer id) {
        return laptopRepository.findById(id).orElseThrow(() ->
                new RecordNotFountException(String.format("laptop %s not found", id)));
    }

    @Override
    public void add(LaptopRegisterDTO laptopRequestDTO) {
        Optional<LaptopEntity> optionalLaptopEntity =
                laptopRepository.findByName(laptopRequestDTO.getName());

        if (optionalLaptopEntity.isPresent()) {
            throw new IllegalArgumentException(
                    String.format("laptop %s already exist", laptopRequestDTO.getName())
            );
        }
        laptopRepository.save(LaptopEntity.of(laptopRequestDTO));
    }

    @Override
    public void delete(Integer id) {
        laptopRepository.findById(id).orElseThrow(() ->
                new RecordNotFountException(String.format("laptop %s not found", id))
        );
        laptopRepository.deleteById(id);
    }

    @Override
    public LaptopEntity update(Integer id, LaptopRegisterDTO laptopRequestDTO) {
        laptopRepository.findById(id).orElseThrow(() ->
                new RecordNotFountException(String.format("laptop %s not found", id))
        );
        return laptopRepository.save(
                LaptopEntity.of(laptopRequestDTO)
        );
    }
}
