package com.example.oauth2ResourseServer.controller;

import com.example.oauth2ResourseServer.dto.request.LaptopRegisterDTO;
import com.example.oauth2ResourseServer.entity.LaptopEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.lifecycle.Startables;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LaptopControllerTest extends BaseTest {

    private static final String PATH_LAPTOP = "/api/laptop/";

    @BeforeAll
    static void beforeAll() {
        Startables.deepStart(postgres).join();
    }


    @AfterEach
    void afterAll() {
        laptopRepository.deleteAll();
    }

    @Test
    @WithMockUser(authorities = "READ")
    @DisplayName("shouldGetLaptopOkStatus")
    void getLaptop() throws Exception {
        LaptopEntity laptopEntity = addMockLaptop();
        callGet(laptopEntity.getId()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "READ")
    @DisplayName("shouldGetLaptopThrowNotFound")
    void getLaptopThrow() throws Exception {
        callGet(3).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "READ")
    @DisplayName("shouldGetLaptopListOkStatus")
    void getList() throws Exception {
        addMockLaptop();
        callList().andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "READ")
    @DisplayName("shouldLaptopListNotFound")
    void getListThrow() throws Exception {
        callList().andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("shouldAddLaptopCreateStatus")
    void addLaptop() throws Exception {
        callAdd().andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("shouldAddLaptopBadRequestStatus")
    void addLaptopThrow() throws Exception {
        callAdd();
        callAdd().andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("shouldDeleteLaptopOkStatus")
    void deleteLaptop() throws Exception {
        LaptopEntity laptopEntity = addMockLaptop();
        callDelete(laptopEntity.getId()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("shouldDeleteLaptopNotFoundStatus")
    void deleteLaptopThrow() throws Exception {
        callDelete(3).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("shouldUpdateLaptopCreatedStatus")
    void updateLaptop() throws Exception {
        LaptopEntity HP = addMockLaptop();
        callUpdate(HP.getId()).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("shouldUpdateLaptopNotFoundStatus")
    void updateLaptopThrow() throws Exception {
        callUpdate(3).andExpect(status().isNotFound());
    }
    private ResultActions callUpdate(Integer id) throws Exception {
        LaptopRegisterDTO laptopRegisterDTO = new LaptopRegisterDTO();
        laptopRegisterDTO.setName("MAC");

        final MockHttpServletRequestBuilder request =
                put(PATH_LAPTOP + "{id}",id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(laptopRegisterDTO));
        return mockMvc.perform(request);
    }
    private ResultActions callDelete(Integer id) throws Exception {
        final MockHttpServletRequestBuilder request =
                delete(PATH_LAPTOP + "{id}", id);
        return mockMvc.perform(request);
    }

    private ResultActions callAdd() throws Exception {
        LaptopRegisterDTO laptopRegisterDTO = new LaptopRegisterDTO();
        laptopRegisterDTO.setName("HP");

        final MockHttpServletRequestBuilder request =
                post(PATH_LAPTOP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(laptopRegisterDTO));
        return mockMvc.perform(request);
    }

    private ResultActions callGet(Integer id) throws Exception {
        final MockHttpServletRequestBuilder request =
                get(PATH_LAPTOP + "{id}", id);
        return mockMvc.perform(request);
    }

    private ResultActions callList() throws Exception {
        final MockHttpServletRequestBuilder request =
                get(PATH_LAPTOP + "list");
        return mockMvc.perform(request);
    }

    private LaptopEntity addMockLaptop() {
        LaptopEntity laptopEntity = new LaptopEntity();
        laptopEntity.setName("HP");
        return laptopRepository.save(laptopEntity);
    }
}

