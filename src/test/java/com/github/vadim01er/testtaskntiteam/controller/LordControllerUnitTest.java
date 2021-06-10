package com.github.vadim01er.testtaskntiteam.controller;

import com.github.vadim01er.testtaskntiteam.service.LordServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@WebMvcTest(LordController.class)
class LordControllerUnitTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private LordServiceImpl lordService;

    @Test
    void getAll() {
    }

    @Test
    void getLoafers() {
    }

    @Test
    void getById() {
    }

    @Test
    void getTop() {
    }

    @Test
    void post() {
    }

    @Test
    void addPlanet() {
    }

    @Test
    void updateLord() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}