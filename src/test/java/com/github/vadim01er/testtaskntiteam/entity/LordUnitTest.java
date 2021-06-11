package com.github.vadim01er.testtaskntiteam.entity;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LordUnitTest {
    private final String someNameLord = "some name lord";
    private final String someNamePlanet = "some name planet";

    @Test
    void update() {
        Planet planet = new Planet() {{
            setId(2L);
            setName(someNamePlanet);
            setLord(null);
        }};
        Lord lord = new Lord() {{
            setId(1L);
            setName(someNameLord);
            setAge(10);
            setPlanets(Collections.singletonList(planet));
        }};
        LordDto lordDto = new LordDto(2L, someNameLord + " new", 20);

        lord.update(lordDto);

        assertEquals(1L, lord.getId());
        assertEquals(lordDto.getName(), lord.getName());
        assertEquals(lordDto.getAge(), lord.getAge());
        assertEquals(Collections.singletonList(planet), lord.getPlanets());
    }

    @Test
    void toDto() {
        Lord lord = new Lord() {{
            setId(1L);
            setName(someNameLord);
            setAge(10);
            setPlanets(Collections.singletonList(new Planet() {{
                setId(2L);
                setName(someNamePlanet);
                setLord(null);
            }}));
        }};

        LordDto lordDto = lord.toDto();

        assertEquals(1L, lordDto.getId());
        assertEquals(someNameLord, lordDto.getName());
        assertEquals(10, lordDto.getAge());
        assertEquals(2L, lordDto.getPlanets().get(0).getId());
        assertEquals(someNamePlanet, lordDto.getPlanets().get(0).getName());
        assertEquals(lordDto, lordDto.getPlanets().get(0).getLord());
    }
}