package com.github.vadim01er.testtaskntiteam.controller;

import com.github.vadim01er.testtaskntiteam.dto.PlanetDto;
import com.github.vadim01er.testtaskntiteam.exception.PlanetNotFoundException;
import com.github.vadim01er.testtaskntiteam.service.PlanetServiceImpl;
import com.github.vadim01er.testtaskntiteam.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.github.vadim01er.testtaskntiteam.utils.Utils.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PlanetControllerUnitTest {

    private final String urlTemplate = "/api/v1/planets";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private Utils utils;
    @MockBean
    private PlanetServiceImpl planetService;

    @Test
    void getAll_ReturnStatusOk_andReturnPlanetDtoList() throws Exception {
        when(planetService.getAll()).thenReturn(Collections.singletonList(utils.getPlanetDtoWithLordDto()));
        mvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(PLANET_ID.intValue())))
                .andExpect(jsonPath("$[0].name", is(SOME_NAME_PLANET)))
                .andExpect(jsonPath("$[0].lord.id", is(LORD_ID.intValue())))
                .andExpect(jsonPath("$[0].lord.name", is(SOME_NAME_LORD)))
                .andExpect(jsonPath("$[0].lord.age", is(LORD_AGE)));
    }

    @Test
    void getAll_ReturnStatusBadRequest_andReturnLordNotFoundException() throws Exception {
        when(planetService.getAll()).thenReturn(Collections.emptyList());
        mvc.perform(get(urlTemplate))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.title", is("Planet not found.")));
    }

    @Test
    void getById_ReturnStatusOk_andReturnPlanetDto() throws Exception {
        when(planetService.getById(PLANET_ID)).thenReturn(utils.getPlanetDtoWithLordDto());
        mvc.perform(get(urlTemplate + "/" + PLANET_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(PLANET_ID.intValue())))
                .andExpect(jsonPath("$.name", is(SOME_NAME_PLANET)))
                .andExpect(jsonPath("$.lord.id", is(LORD_ID.intValue())))
                .andExpect(jsonPath("$.lord.name", is(SOME_NAME_LORD)))
                .andExpect(jsonPath("$.lord.age", is(LORD_AGE)));
    }

    @Test
    void getById_ReturnStatusBadRequest_andReturnLordNotFoundException() throws Exception {
        when(planetService.getById(PLANET_ID)).thenThrow(new PlanetNotFoundException(PLANET_ID));
        mvc.perform(get(urlTemplate + "/" + PLANET_ID))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.title", is("Planet by '" + PLANET_ID + "' not found.")));
    }

    @Test
    void post_ReturnStatusCreated_AndReturnPlanetDto() throws Exception {
        PlanetDto planetDto = utils.getPlanetDtoForPost();
        when(planetService.add(any())).thenReturn(utils.getPlanetDto());
        mvc.perform(
                post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(planetDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(PLANET_ID.intValue())))
                .andExpect(jsonPath("$.name", is(SOME_NAME_PLANET)))
                .andExpect(jsonPath("$.lord", nullValue()));
    }

    @Test
    void post_ReturnStatusBadRequest_AndReturnInvalidJsonException() throws Exception {
        mvc.perform(
                post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(utils.getPlanetDtoWithLordDto())))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("Argument not valid")))
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors[0]", PLANET_MUST_BE_NULL))
                .andExpect(jsonPath("$.errors[1]", PLANET_MUST_BE_NULL));

        mvc.perform(
                post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("Argument not valid")))
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors[0]", MUST_ANY_OF))
                .andExpect(jsonPath("$.errors[1]", MUST_ANY_OF));

        mvc.perform(
                post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \" \"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("Argument not valid")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("name -> must not be blank")));
    }

    @Test
    void post_ReturnStatusBadRequest_AndReturnUnformedJsonException() throws Exception {
        mvc.perform(
                post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"sdf}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("Invalid json in body")));
    }

    @Test
    void update_ReturnStatusOk_AndReturnPlanetDto() throws Exception {
        when(planetService.update(eq(PLANET_ID), any()))
                .thenReturn(utils.getPlanetDto("new name"));
        mvc.perform(
                put(urlTemplate + "/" + PLANET_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"new name\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(PLANET_ID.intValue())))
                .andExpect(jsonPath("$.name", is("new name")))
                .andExpect(jsonPath("$.lord", nullValue()));
    }

    @Test
    void update_ReturnStatusBadRequest_AndReturnPlanetNotFoundException() throws Exception {
        when(planetService.update(eq(PLANET_ID), any()))
                .thenThrow(new PlanetNotFoundException(PLANET_ID));
        mvc.perform(
                put(urlTemplate + "/" + PLANET_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"new name\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.title", is("Planet by '" + PLANET_ID + "' not found.")));
    }

    @Test
    void update_ReturnStatusBadRequest_AndReturnInvalidJsonException() throws Exception {
        mvc.perform(
                put(urlTemplate + "/" + PLANET_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(utils.getPlanetDtoWithLordDto())))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("Argument not valid")))
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors[0]", PLANET_MUST_BE_NULL))
                .andExpect(jsonPath("$.errors[1]", PLANET_MUST_BE_NULL));

        mvc.perform(
                put(urlTemplate + "/" + LORD_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("Argument not valid")))
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors[0]", MUST_ANY_OF))
                .andExpect(jsonPath("$.errors[1]", MUST_ANY_OF));

        mvc.perform(
                put(urlTemplate + "/" + LORD_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \" \"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("Argument not valid")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("name -> must not be blank")));
    }
    @Test
    void deleteById_ReturnStatusNoContent_AndReturnOk() throws Exception {
        mvc.perform(delete(urlTemplate + "/" + PLANET_ID))
                .andExpect(status().isNoContent())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.NO_CONTENT.value())));
    }
}