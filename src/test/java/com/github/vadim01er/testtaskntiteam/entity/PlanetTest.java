package com.github.vadim01er.testtaskntiteam.entity;

import com.github.vadim01er.testtaskntiteam.dto.PlanetDto;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.github.vadim01er.testtaskntiteam.utils.Utils.SOME_NAME_LORD;
import static com.github.vadim01er.testtaskntiteam.utils.Utils.SOME_NAME_PLANET;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlanetTest {

    @Test
    void update() {
        Lord lord = new Lord() {{
            setId(1L);
            setName(SOME_NAME_LORD);
            setAge(10);
            setPlanets(Collections.emptyList());
        }};
        Planet planet = new Planet() {{
            setId(2L);
            setName(SOME_NAME_PLANET);
            setLord(lord);
        }};
        lord.setPlanets(Collections.singletonList(planet));
        PlanetDto planetDto = new PlanetDto(SOME_NAME_LORD + " new");

        planet.update(planetDto);

        assertEquals(2L, planet.getId());
        assertEquals(planetDto.getName(), planet.getName());
        assertEquals(lord, planet.getLord());
    }

}