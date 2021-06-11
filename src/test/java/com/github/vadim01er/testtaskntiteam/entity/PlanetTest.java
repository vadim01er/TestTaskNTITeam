package com.github.vadim01er.testtaskntiteam.entity;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlanetTest {
    private final String someNameLord = "some name lord";
    private final String someNamePlanet = "some name planet";

    @Test
    void update() {
        Lord lord = new Lord() {{
            setId(1L);
            setName(someNameLord);
            setAge(10);
            setPlanets(Collections.emptyList());
        }};
        Planet planet = new Planet() {{
            setId(2L);
            setName(someNamePlanet);
            setLord(lord);
        }};
        lord.setPlanets(Collections.singletonList(planet));
        PlanetDto planetDto = new PlanetDto(1L, someNameLord + " new", null);

        planet.update(planetDto);

        assertEquals(2L, planet.getId());
        assertEquals(planetDto.getName(), planet.getName());
        assertEquals(lord, planet.getLord());
    }

    @Test
    void toDto() {
        Lord lord = new Lord() {{
            setId(2L);
            setName(someNameLord);
            setAge(10);
            setPlanets(Collections.emptyList());
        }};
        Planet planet = new Planet() {{
            setId(1L);
            setName(someNamePlanet);
            setLord(lord);
        }};
        lord.setPlanets(Collections.singletonList(planet));

        PlanetDto planetDto = planet.toDto();

        assertEquals(1L, planetDto.getId());
        assertEquals(someNamePlanet, planetDto.getName());
        assertEquals(lord, planet.getLord());
    }
}