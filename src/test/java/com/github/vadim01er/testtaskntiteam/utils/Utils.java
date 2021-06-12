package com.github.vadim01er.testtaskntiteam.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vadim01er.testtaskntiteam.entity.Lord;
import com.github.vadim01er.testtaskntiteam.entity.LordDto;
import com.github.vadim01er.testtaskntiteam.entity.Planet;
import com.github.vadim01er.testtaskntiteam.entity.PlanetDto;
import org.hamcrest.Matcher;
import org.hamcrest.core.AnyOf;

import java.util.Collections;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

/**
 * Utils class. Use for help all test get persistent methods and fields.
 */
public class Utils {

    public static final Long LORD_ID = 12L;
    public static final Long PLANET_ID = 13L;
    public static final String SOME_NAME_LORD = "some name lord";
    public static final String SOME_NAME_PLANET = "some name planet";
    public static final Integer LORD_AGE = 20;

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    public static final AnyOf<String> PLANET_MUST_BE_NULL = anyOf(
            is("id -> must be null"),
            is("lord -> must be null")
    );

    public static final AnyOf<String> LORD_MUST_BE_NULL = anyOf(
            is("id -> must be null"),
            is("planets -> must be null")
    );

    public static final AnyOf<String> MUST_ANY_OF = anyOf(
            is("name -> length must be between 1 and 250"),
            is("name -> must not be blank")
    );

    public static final Matcher<String> AGE_LESS =
            is("age -> must be less than or equal to 200");

    public static final Matcher<String> AGE_GREATER =
            is("age -> must be greater than or equal to 1");

    public static final Matcher<String> ARGUMENT_NOT_VALID =
            is("Argument not valid");

    public static final Matcher<String> LORD_NOT_FOUND =
            is("Lord not found.");

    public static Lord getLordWithPlanet() {
        Planet planet = new Planet() {{
            setId(PLANET_ID);
            setName(SOME_NAME_PLANET);
        }};
        Lord lord = new Lord() {{
            setId(LORD_ID);
            setName(SOME_NAME_LORD);
            setAge(LORD_AGE);
            setPlanets(Collections.singletonList(planet));
        }};
        planet.setLord(lord);
        return lord;
    }

    public static LordDto getLordDtoWithPlanetDto() {
        return getLordWithPlanet().toDto();
    }

    public static LordDto getLordDto() {
        return getLord(SOME_NAME_LORD, LORD_AGE).toDto();
    }

    public static Lord getLord(String name, Integer age) {
        return new Lord() {{
            setId(LORD_ID);
            setName(name);
            setAge(age);
            setPlanets(Collections.emptyList());
        }};
    }

    public static Lord getLord() {
        return new Lord() {{
            setId(LORD_ID);
            setName(SOME_NAME_LORD);
            setAge(LORD_AGE);
            setPlanets(Collections.emptyList());
        }};
    }

    public static PlanetDto getPlanetDtoWithLordDto() {
        return getPlanetWithLord().toDto();
    }

    public static Planet getPlanetWithLord() {
        Planet planet = new Planet() {{
            setId(PLANET_ID);
            setName(SOME_NAME_PLANET);
        }};
        Lord lord = new Lord() {{
            setId(LORD_ID);
            setName(SOME_NAME_LORD);
            setAge(LORD_AGE);
            setPlanets(Collections.singletonList(planet));
        }};
        planet.setLord(lord);
        return planet;
    }

    public static Planet getPlanet() {
        return new Planet() {{
            setId(PLANET_ID);
            setName(SOME_NAME_PLANET);
        }};
    }


    public static LordDto getLordDto(String newName, Integer age) {
        return getLord(newName, age).toDto();
    }

    public static LordDto getLordDtoForPost() {
        return new LordDto(SOME_NAME_LORD, LORD_AGE);
    }

    public static PlanetDto getPlanetDtoForPost() {
        return new PlanetDto(SOME_NAME_PLANET);
    }

    public static PlanetDto getPlanetDto() {
        return getPlanetDto(SOME_NAME_PLANET);
    }

    public static PlanetDto getPlanetDto(String name) {
        Planet planet = new Planet() {{
            setId(PLANET_ID);
            setName(name);
        }};
        return planet.toDto();
    }
}
