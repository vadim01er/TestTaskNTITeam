package com.github.vadim01er.testtaskntiteam.controller;

import com.github.vadim01er.testtaskntiteam.dto.PlanetDto;
import com.github.vadim01er.testtaskntiteam.exception.LordNotFoundException;
import com.github.vadim01er.testtaskntiteam.exception.PlanetNotFoundException;
import com.github.vadim01er.testtaskntiteam.service.LordServiceImpl;
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
import java.util.List;

import static com.github.vadim01er.testtaskntiteam.utils.Utils.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LordControllerUnitTest {

    private final String urlTemplate = "/api/v1/lords";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private Utils utils;
    @MockBean
    private LordServiceImpl lordService;

    @Test
    void getAll_ReturnStatusOk_andReturnLordDtoList() throws Exception {
        when(lordService.getAll()).thenReturn(Collections.singletonList(utils.getLordDtoWithPlanetDto()));
        mvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(LORD_ID.intValue())))
                .andExpect(jsonPath("$[0].name", is(SOME_NAME_LORD)))
                .andExpect(jsonPath("$[0].age", is(LORD_AGE)))
                .andExpect(jsonPath("$[0].planets", hasSize(1)))
                .andExpect(jsonPath("$[0].planets[0].id", is(13)))
                .andExpect(jsonPath("$[0].planets[0].name", is(SOME_NAME_PLANET)));
    }

    @Test
    void getAll_ReturnStatusBadRequest_andReturnLordNotFoundException() throws Exception {
        when(lordService.getAll()).thenReturn(Collections.emptyList());
        mvc.perform(get(urlTemplate))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.title", LORD_NOT_FOUND));
    }

    @Test
    void getLoafers_ReturnStatusOk_AndReturnLordDtoList() throws Exception {
        when(lordService.getLoafers()).thenReturn(List.of(utils.getLordDto()));
        mvc.perform(get(urlTemplate + "/loafers"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(LORD_ID.intValue())))
                .andExpect(jsonPath("$[0].name", is(SOME_NAME_LORD)))
                .andExpect(jsonPath("$[0].age", is(LORD_AGE)))
                .andExpect(jsonPath("$[0].planets", hasSize(0)));
    }

    @Test
    void getLoafers_ReturnStatusBadRequest_AndReturnLordNotFoundException() throws Exception {
        when(lordService.getLoafers()).thenReturn(Collections.emptyList());
        mvc.perform(get(urlTemplate + "/loafers"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.title", LORD_NOT_FOUND));
    }

    @Test
    void getById_ReturnStatusOk_andReturnLordDto() throws Exception {
        when(lordService.getById(LORD_ID)).thenReturn(utils.getLordDtoWithPlanetDto());
        mvc.perform(get(urlTemplate + "/" + LORD_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(LORD_ID.intValue())))
                .andExpect(jsonPath("$.name", is(SOME_NAME_LORD)))
                .andExpect(jsonPath("$.age", is(LORD_AGE)))
                .andExpect(jsonPath("$.planets", hasSize(1)))
                .andExpect(jsonPath("$.planets[0].id", is(PLANET_ID.intValue())))
                .andExpect(jsonPath("$.planets[0].name", is(SOME_NAME_PLANET)));
    }

    @Test
    void getById_ReturnStatusBadRequest_andReturnLordNotFoundException() throws Exception {
        when(lordService.getById(LORD_ID)).thenThrow(new LordNotFoundException(LORD_ID));
        mvc.perform(get(urlTemplate + "/" + LORD_ID))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.title", is("Lord by '" + LORD_ID + "' not found.")));
    }

    @Test
    void getTop_ReturnStatusOk_AndReturnLordDtoList() throws Exception {
        when(lordService.getTopTen()).thenReturn(Collections.singletonList(utils.getLordDto()));
        mvc.perform(get(urlTemplate + "/top"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(LORD_ID.intValue())))
                .andExpect(jsonPath("$[0].name", is(SOME_NAME_LORD)))
                .andExpect(jsonPath("$[0].age", is(LORD_AGE)))
                .andExpect(jsonPath("$[0].planets", hasSize(0)));
    }

    @Test
    void getTop_ReturnStatusBadRequest_AndReturnLordNotFoundException() throws Exception {
        when(lordService.getLoafers()).thenReturn(Collections.emptyList());
        mvc.perform(get(urlTemplate + "/top"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.title", LORD_NOT_FOUND));
    }

    @Test
    void post_ReturnStatusCreated_AndReturnLordDto() throws Exception {
        when(lordService.add(any())).thenReturn(utils.getLordDto());
        mvc.perform(
                post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(utils.getLordDtoForPost())))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(LORD_ID.intValue())))
                .andExpect(jsonPath("$.name", is(SOME_NAME_LORD)))
                .andExpect(jsonPath("$.age", is(LORD_AGE)))
                .andExpect(jsonPath("$.planets", is(Collections.emptyList())));
    }

    @Test
    void post_ReturnStatusBadRequest_AndReturnInvalidJsonException() throws Exception {
        mvc.perform(
                post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(utils.getLordDto())))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", ARGUMENT_NOT_VALID))
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors[0]", Utils.LORD_MUST_BE_NULL))
                .andExpect(jsonPath("$.errors[1]", Utils.LORD_MUST_BE_NULL));

        mvc.perform(
                post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\", \"age\":10}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", ARGUMENT_NOT_VALID))
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors[0]", Utils.MUST_ANY_OF))
                .andExpect(jsonPath("$.errors[1]", Utils.MUST_ANY_OF));

        mvc.perform(
                post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"some name\", \"age\":0}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", ARGUMENT_NOT_VALID))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", AGE_GREATER));

        mvc.perform(
                post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"some name\", \"age\":201}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", ARGUMENT_NOT_VALID))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", AGE_LESS));
    }

    @Test
    void post_ReturnStatusBadRequest_AndReturnUnformedJsonException() throws Exception {
        mvc.perform(
                post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"sdf, \"age\":}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("Invalid json in body")));
    }

    @Test
    void addPlanet_ReturnStatusCreated_AndReturnPlanetDto() throws Exception {
        PlanetDto planetDto = utils.getPlanetDtoForPost();
        when(lordService.addPlanet(eq(LORD_ID), eq(planetDto))).thenReturn(utils.getPlanetDtoWithLordDto());
        mvc.perform(
                post(urlTemplate + "/" + LORD_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(planetDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(PLANET_ID.intValue())))
                .andExpect(jsonPath("$.name", is(SOME_NAME_PLANET)))
                .andExpect(jsonPath("$.lord.id", is(LORD_ID.intValue())))
                .andExpect(jsonPath("$.lord.name", is(SOME_NAME_LORD)))
                .andExpect(jsonPath("$.lord.age", is(LORD_AGE)));
    }

    @Test
    void addPlanet_ReturnStatusBadRequest_AndReturnInvalidJsonException() throws Exception {
        mvc.perform(
                post(urlTemplate + "/" + LORD_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(utils.getPlanetDtoWithLordDto())))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", ARGUMENT_NOT_VALID))
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors[0]", Utils.PLANET_MUST_BE_NULL))
                .andExpect(jsonPath("$.errors[1]", Utils.PLANET_MUST_BE_NULL));

        mvc.perform(
                post(urlTemplate + "/" + LORD_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", ARGUMENT_NOT_VALID))
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors[0]", Utils.MUST_ANY_OF))
                .andExpect(jsonPath("$.errors[0]", Utils.MUST_ANY_OF));
    }

    @Test
    void addPlanet_ReturnStatusBadRequest_AndReturnUnformedJsonException() throws Exception {
        when(lordService.addPlanet(eq(LORD_ID), any())).thenReturn(utils.getPlanetDtoWithLordDto());
        mvc.perform(
                post(urlTemplate + "/" + LORD_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"sdf }"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("Invalid json in body")));
    }

    @Test
    void addPlanet_ReturnStatusBadRequest_AndReturnLordNotFoundException() throws Exception {
        when(lordService.addPlanet(eq(LORD_ID), any())).thenThrow(new LordNotFoundException(LORD_ID));
        mvc.perform(
                post(urlTemplate + "/" + LORD_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"sdf\" }"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.title", is("Lord by '" + LORD_ID + "' not found.")));
    }

    @Test
    void update_ReturnStatusOk_AndReturnLordDto() throws Exception {
        when(lordService.update(eq(LORD_ID), any())).thenReturn(utils.getLordDto("new name", 21));
        mvc.perform(
                put(urlTemplate + "/" + LORD_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"new name\", \"age\": 21}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(LORD_ID.intValue())))
                .andExpect(jsonPath("$.name", is("new name")))
                .andExpect(jsonPath("$.age", is(21)))
                .andExpect(jsonPath("$.planets", is(Collections.emptyList())));
    }

    @Test
    void update_ReturnStatusBadRequest_AndReturnLordNotFoundException() throws Exception {
        when(lordService.update(eq(LORD_ID), any())).thenThrow(new LordNotFoundException(LORD_ID));
        mvc.perform(
                put(urlTemplate + "/" + LORD_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"new name\", \"age\": 21}"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.title", is("Lord by '" + LORD_ID + "' not found.")));
    }

    @Test
    void update_ReturnStatusBadRequest_AndReturnInvalidJsonException() throws Exception {
        mvc.perform(
                put(urlTemplate + "/" + LORD_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(utils.getLordDto())))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", ARGUMENT_NOT_VALID))
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors[0]", Utils.LORD_MUST_BE_NULL))
                .andExpect(jsonPath("$.errors[1]", Utils.LORD_MUST_BE_NULL));

        mvc.perform(
                put(urlTemplate + "/" + LORD_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\", \"age\":10}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", ARGUMENT_NOT_VALID))
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors[0]", Utils.MUST_ANY_OF))
                .andExpect(jsonPath("$.errors[1]", Utils.MUST_ANY_OF));

        mvc.perform(
                put(urlTemplate + "/" + LORD_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"some name\", \"age\":0}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", ARGUMENT_NOT_VALID))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", AGE_GREATER));

        mvc.perform(
                put(urlTemplate + "/" + LORD_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"some name\", \"age\":201}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", ARGUMENT_NOT_VALID))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", AGE_LESS));
    }

    @Test
    void assignPlanetToLord_ReturnStatusOk_AndReturnPlanetDto() throws Exception {
        when(lordService.assignToManagePlanet(eq(LORD_ID), eq(PLANET_ID)))
                .thenReturn(utils.getPlanetDtoWithLordDto());
        mvc.perform(
                put(urlTemplate + "/" + LORD_ID + "/assign_planet")
                        .param("planet_id", String.valueOf(PLANET_ID)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(PLANET_ID.intValue())))
                .andExpect(jsonPath("$.name", is(SOME_NAME_PLANET)))
                .andExpect(jsonPath("$.lord.id", is(LORD_ID.intValue())))
                .andExpect(jsonPath("$.lord.name", is(SOME_NAME_LORD)))
                .andExpect(jsonPath("$.lord.age", is(LORD_AGE)));
    }

    @Test
    void assignPlanetToLord_ReturnStatusBadRequest_AndReturnLordNotFoundException() throws Exception {
        when(lordService.assignToManagePlanet(eq(LORD_ID), eq(PLANET_ID)))
                .thenThrow(new LordNotFoundException(LORD_ID));
        mvc.perform(
                put(urlTemplate + "/" + LORD_ID + "/assign_planet")
                        .param("planet_id", String.valueOf(PLANET_ID)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.title", is("Lord by '" + LORD_ID + "' not found.")));
    }

    @Test
    void assignPlanetToLord_ReturnStatusBadRequest_AndReturnPlanetNotFoundException() throws Exception {
        when(lordService.assignToManagePlanet(eq(LORD_ID), eq(PLANET_ID)))
                .thenThrow(new PlanetNotFoundException(PLANET_ID));
        mvc.perform(
                put(urlTemplate + "/" + LORD_ID + "/assign_planet")
                        .param("planet_id", String.valueOf(PLANET_ID)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.title", is("Planet by '" + PLANET_ID + "' not found.")));
    }

    @Test
    void deleteById_ReturnStatusNoContent_AndReturnOk() throws Exception {
        mvc.perform(delete(urlTemplate + "/" + LORD_ID))
                .andExpect(status().isNoContent())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.NO_CONTENT.value())));
    }

    @Test
    void deleteById_ReturnStatusBadRequest_AndReturnLordNotFoundException() throws Exception {
        doThrow(new LordNotFoundException(LORD_ID)).when(lordService).deleteById(LORD_ID);
        mvc.perform(delete(urlTemplate + "/" + LORD_ID))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.title", is("Lord by '" + LORD_ID + "' not found.")));
    }
}