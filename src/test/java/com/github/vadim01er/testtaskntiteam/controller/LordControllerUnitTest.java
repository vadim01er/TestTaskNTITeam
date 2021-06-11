package com.github.vadim01er.testtaskntiteam.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vadim01er.testtaskntiteam.entity.Lord;
import com.github.vadim01er.testtaskntiteam.entity.LordDto;
import com.github.vadim01er.testtaskntiteam.entity.Planet;
import com.github.vadim01er.testtaskntiteam.entity.PlanetDto;
import com.github.vadim01er.testtaskntiteam.exception.LordNotFoundException;
import com.github.vadim01er.testtaskntiteam.exception.PlanetNotFoundException;
import com.github.vadim01er.testtaskntiteam.service.LordServiceImpl;
import org.hamcrest.Matchers;
import org.hamcrest.core.AnyOf;
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

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class LordControllerUnitTest {
    private final Long lordId = 12L;
    private final Long planetId = 13L;
    private final String someNameLord = "some name lord";
    private final String someNamePlanet = "some name planet";
    private final Integer lordAge = 20;

    private final String urlTemplate = "/api/v1/lords";

    private final ObjectMapper objectMapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Autowired
    private MockMvc mvc;
    @MockBean
    private LordServiceImpl lordService;

    private LordDto getLordDtoWithPlanetDto() {
        return getLordWithPlanet().toDto();
    }

    private Lord getLordWithPlanet() {
        Planet planet = new Planet() {{
            setId(planetId);
            setName(someNamePlanet);
        }};
        Lord lord = new Lord() {{
            setId(lordId);
            setName(someNameLord);
            setAge(lordAge);
            setPlanets(Collections.singletonList(planet));
        }};
        planet.setLord(lord);
        return lord;
    }

    private LordDto getLordDto() {
        return getLord(someNameLord, lordAge).toDto();
    }

    private Lord getLord(String name, Integer age) {
        return new Lord() {{
            setId(lordId);
            setName(name);
            setAge(age);
            setPlanets(Collections.emptyList());
        }};
    }

    private PlanetDto getPlanetDtoWithLord() {
        Planet planet = new Planet() {{
            setId(planetId);
            setName(someNamePlanet);
        }};
        Lord lord = new Lord() {{
            setId(lordId);
            setName(someNameLord);
            setAge(lordAge);
            setPlanets(Collections.singletonList(planet));
        }};
        planet.setLord(lord);
        return planet.toDto();
    }


    private LordDto getLordDto(String newName, Integer age) {
        return getLord(newName, age).toDto();
    }

    private LordDto getLordDtoForPost() {
        return new LordDto(someNameLord, lordAge);
    }

    private PlanetDto getPlanetDtoForPost() {
        return new PlanetDto(someNamePlanet);
    }

    @Test
    void getAll_ReturnStatusOk_andReturnLordDtoList() throws Exception {
        when(lordService.getAll()).thenReturn(Collections.singletonList(getLordDtoWithPlanetDto()));
        mvc.perform(get("/api/v1/lords"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(lordId.intValue())))
                .andExpect(jsonPath("$[0].name", is(someNameLord)))
                .andExpect(jsonPath("$[0].age", is(lordAge)))
                .andExpect(jsonPath("$[0].planets", hasSize(1)))
                .andExpect(jsonPath("$[0].planets[0].id", is(13)))
                .andExpect(jsonPath("$[0].planets[0].name", is(someNamePlanet)));
    }

    @Test
    void getAll_ReturnStatusBadRequest_andReturnLordNotFoundException() throws Exception {
        when(lordService.getLoafers()).thenReturn(Collections.emptyList());
        mvc.perform(get(urlTemplate))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("Lord not found.")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("Lord not found.")));
    }

    @Test
    void getLoafers_ReturnStatusOk_AndReturnLordDtoList() throws Exception {
        when(lordService.getLoafers()).thenReturn(List.of(getLordDto()));
        mvc.perform(get(urlTemplate + "/loafers"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(lordId.intValue())))
                .andExpect(jsonPath("$[0].name", is(someNameLord)))
                .andExpect(jsonPath("$[0].age", is(lordAge)))
                .andExpect(jsonPath("$[0].planets", hasSize(0)));
    }

    @Test
    void getLoafers_ReturnStatusBadRequest_AndReturnLordNotFoundException() throws Exception {
        when(lordService.getLoafers()).thenReturn(Collections.emptyList());
        mvc.perform(get(urlTemplate + "/loafers"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("Lord not found.")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("Lord not found.")));
    }

    @Test
    void getById_ReturnStatusOk_andReturnLordDto() throws Exception {
        when(lordService.getById(lordId)).thenReturn(getLordDtoWithPlanetDto());
        mvc.perform(get(urlTemplate + "/" + lordId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(lordId.intValue())))
                .andExpect(jsonPath("$.name", is(someNameLord)))
                .andExpect(jsonPath("$.age", is(lordAge)))
                .andExpect(jsonPath("$.planets", hasSize(1)))
                .andExpect(jsonPath("$.planets[0].id", is(planetId.intValue())))
                .andExpect(jsonPath("$.planets[0].name", is(someNamePlanet)));
    }

    @Test
    void getById_ReturnStatusBadRequest_andReturnLordNotFoundException() throws Exception {
        when(lordService.getById(lordId)).thenThrow(new LordNotFoundException(lordId));
        mvc.perform(get(urlTemplate + "/" + lordId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("Lord by '" + lordId + "' not found.")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("Lord by '" + lordId + "' not found.")));
    }

    @Test
    void getTop_ReturnStatusOk_AndReturnLordDtoList() throws Exception {
        when(lordService.getTopTen()).thenReturn(Collections.singletonList(getLordDto()));
        mvc.perform(get(urlTemplate + "/top"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(lordId.intValue())))
                .andExpect(jsonPath("$[0].name", is(someNameLord)))
                .andExpect(jsonPath("$[0].age", is(lordAge)))
                .andExpect(jsonPath("$[0].planets", hasSize(0)));
    }

    @Test
    void getTop_ReturnStatusBadRequest_AndReturnLordNotFoundException() throws Exception {
        when(lordService.getLoafers()).thenReturn(Collections.emptyList());
        mvc.perform(get(urlTemplate + "/top"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("Lord not found.")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("Lord not found.")));
    }

    @Test
    void post_ReturnStatusCreated_AndReturnLordDto() throws Exception {
        LordDto lordDto = getLordDtoForPost();
        when(lordService.add(any())).thenReturn(getLordDto());
        mvc.perform(
                post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lordDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(lordId.intValue())))
                .andExpect(jsonPath("$.name", is(someNameLord)))
                .andExpect(jsonPath("$.age", is(lordAge)))
                .andExpect(jsonPath("$.planets", is(Collections.emptyList())));
    }

    @Test
    void post_ReturnStatusBadRequest_AndReturnInvalidJsonException() throws Exception {
        AnyOf<String> mustAnyOf = anyOf(
                is("length must be between 1 and 250"),
                is("must not be blank")
        );

        mvc.perform(
                post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getLordDto())))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", Matchers.startsWith("No valid json field:")))
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors[0]", is("must be null")))
                .andExpect(jsonPath("$.errors[1]", is("must be null")));

        mvc.perform(
                post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\", \"age\":10}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("No valid json field: [name]")))
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors[0]", mustAnyOf))
                .andExpect(jsonPath("$.errors[1]", mustAnyOf));

        mvc.perform(
                post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"some name\", \"age\":0}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("No valid json field: [age]")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("must be greater than or equal to 1")));

        mvc.perform(
                post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"some name\", \"age\":201}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("No valid json field: [age]")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("must be less than or equal to 200")));
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
                .andExpect(jsonPath("$.title", is("Unformed json in body")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("Unformed json in body")));
    }

    @Test
    void addPlanet_ReturnStatusCreated_AndReturnPlanetDto() throws Exception {
        PlanetDto planetDto = getPlanetDtoForPost();
        when(lordService.addPlanet(eq(lordId), eq(planetDto))).thenReturn(getPlanetDtoWithLord());
        mvc.perform(
                post(urlTemplate + "/" + lordId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(planetDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(planetId.intValue())))
                .andExpect(jsonPath("$.name", is(someNamePlanet)))
                .andExpect(jsonPath("$.lord.id", is(lordId.intValue())))
                .andExpect(jsonPath("$.lord.name", is(someNameLord)))
                .andExpect(jsonPath("$.lord.age", is(lordAge)));
    }

    @Test
    void addPlanet_ReturnStatusBadRequest_AndReturnInvalidJsonException() throws Exception {
        PlanetDto planetDto = getPlanetDtoWithLord();
        AnyOf<String> mustAnyOf = anyOf(
                is("length must be between 1 and 250"),
                is("must not be blank")
        );

        mvc.perform(
                post(urlTemplate + "/" + lordId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(planetDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", Matchers.startsWith("No valid json field:")))
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors[0]", is("must be null")))
                .andExpect(jsonPath("$.errors[1]", is("must be null")));

        mvc.perform(
                post(urlTemplate + "/" + lordId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("No valid json field: [name]")))
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors[0]", mustAnyOf))
                .andExpect(jsonPath("$.errors[0]", mustAnyOf));

    }

    @Test
    void addPlanet_ReturnStatusBadRequest_AndReturnUnformedJsonException() throws Exception {
        when(lordService.addPlanet(eq(lordId), any())).thenReturn(getPlanetDtoWithLord());
        mvc.perform(
                post(urlTemplate + "/" + lordId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"sdf }"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("Unformed json in body")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("Unformed json in body")));
    }

    @Test
    void addPlanet_ReturnStatusBadRequest_AndReturnLordNotFoundException() throws Exception {
        when(lordService.addPlanet(eq(lordId), any())).thenThrow(new LordNotFoundException(lordId));
        mvc.perform(
                post(urlTemplate + "/" + lordId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"sdf\" }"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("Lord by '" + lordId + "' not found.")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("Lord by '" + lordId + "' not found.")));
    }

    @Test
    void update_ReturnStatusCreated_AndReturnLordDto() throws Exception {
        when(lordService.update(eq(lordId), any())).thenReturn(getLordDto("new name", 21));
        mvc.perform(
                put(urlTemplate + "/" + lordId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"new name\", \"age\": 21}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(lordId.intValue())))
                .andExpect(jsonPath("$.name", is("new name")))
                .andExpect(jsonPath("$.age", is(21)))
                .andExpect(jsonPath("$.planets", is(Collections.emptyList())));
    }

    @Test
    void update_ReturnStatusBadRequest_AndReturnLordNotFoundException() throws Exception {
        when(lordService.update(eq(lordId), any())).thenThrow(new LordNotFoundException(lordId));
        mvc.perform(
                put(urlTemplate + "/" + lordId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"new name\", \"age\": 21}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("Lord by '" + lordId + "' not found.")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("Lord by '" + lordId + "' not found.")));
    }

    @Test
    void update_ReturnStatusBadRequest_AndReturnInvalidJsonException() throws Exception {
        AnyOf<String> mustAnyOf = anyOf(
                is("length must be between 1 and 250"),
                is("must not be blank")
        );
        mvc.perform(
                put(urlTemplate + "/" + lordId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getLordDto())))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", Matchers.startsWith("No valid json field:")))
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors[0]", is("must be null")))
                .andExpect(jsonPath("$.errors[1]", is("must be null")));

        mvc.perform(
                put(urlTemplate + "/" + lordId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\", \"age\":10}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("No valid json field: [name]")))
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors[0]", mustAnyOf))
                .andExpect(jsonPath("$.errors[1]", mustAnyOf));

        mvc.perform(
                put(urlTemplate + "/" + lordId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"some name\", \"age\":0}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("No valid json field: [age]")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("must be greater than or equal to 1")));

        mvc.perform(
                put(urlTemplate + "/" + lordId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"some name\", \"age\":201}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("No valid json field: [age]")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("must be less than or equal to 200")));
    }

    @Test
    void assignPlanetToLord_ReturnStatusOk_AndReturnPlanetDto() throws Exception {
        when(lordService.assignToManagePlanet(eq(lordId), eq(planetId)))
                .thenReturn(getPlanetDtoWithLord());
        mvc.perform(
                put(urlTemplate + "/" + lordId + "/assign_planet")
                        .param("planet_id", String.valueOf(planetId)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(planetId.intValue())))
                .andExpect(jsonPath("$.name", is(someNamePlanet)))
                .andExpect(jsonPath("$.lord.id", is(lordId.intValue())))
                .andExpect(jsonPath("$.lord.name", is(someNameLord)))
                .andExpect(jsonPath("$.lord.age", is(lordAge)));
    }

    @Test
    void assignPlanetToLord_ReturnStatusBadRequest_AndReturnLordNotFoundException() throws Exception {
        when(lordService.assignToManagePlanet(eq(lordId), eq(planetId)))
                .thenThrow(new LordNotFoundException(lordId));
        mvc.perform(
                put(urlTemplate + "/" + lordId + "/assign_planet")
                        .param("planet_id", String.valueOf(planetId)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("Lord by '" + lordId + "' not found.")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("Lord by '" + lordId + "' not found.")));
    }

    @Test
    void assignPlanetToLord_ReturnStatusBadRequest_AndReturnPlanetNotFoundException() throws Exception {
        when(lordService.assignToManagePlanet(eq(lordId), eq(planetId)))
                .thenThrow(new PlanetNotFoundException(planetId));
        mvc.perform(
                put(urlTemplate + "/" + lordId + "/assign_planet")
                        .param("planet_id", String.valueOf(planetId)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("Planet by '" + planetId + "' not found.")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("Planet by '" + planetId + "' not found.")));
    }

    @Test
    void deleteById_ReturnStatusNoContent_AndReturnOk() throws Exception {
        mvc.perform(delete(urlTemplate + "/" + lordId))
                .andExpect(status().isNoContent())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.NO_CONTENT.value())));
    }

    @Test
    void deleteById_ReturnStatusBadRequest_AndReturnLordNotFoundException() throws Exception {
        doThrow(new LordNotFoundException(lordId)).when(lordService).deleteById(lordId);
        mvc.perform(delete(urlTemplate + "/" + lordId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.title", is("Lord by '" + lordId + "' not found.")))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]", is("Lord by '" + lordId + "' not found.")));
    }
}