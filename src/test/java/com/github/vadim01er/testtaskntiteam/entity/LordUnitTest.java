package com.github.vadim01er.testtaskntiteam.entity;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.github.vadim01er.testtaskntiteam.utils.Utils.SOME_NAME_LORD;
import static com.github.vadim01er.testtaskntiteam.utils.Utils.SOME_NAME_PLANET;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LordUnitTest {

    @Test
    void update() {
        Planet planet = new Planet() {{
            setId(2L);
            setName(SOME_NAME_PLANET);
            setLord(null);
        }};
        Lord lord = new Lord() {{
            setId(1L);
            setName(SOME_NAME_LORD);
            setAge(10);
            setPlanets(Collections.singletonList(planet));
        }};
        LordDto lordDto = new LordDto(2L, SOME_NAME_LORD + " new", 20);

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
            setName(SOME_NAME_LORD);
            setAge(10);
            setPlanets(Collections.singletonList(new Planet() {{
                setId(2L);
                setName(SOME_NAME_PLANET);
                setLord(null);
            }}));
        }};

        LordDto lordDto = lord.toDto();

        assertEquals(1L, lordDto.getId());
        assertEquals(SOME_NAME_LORD, lordDto.getName());
        assertEquals(10, lordDto.getAge());
        assertEquals(2L, lordDto.getPlanets().get(0).getId());
        assertEquals(SOME_NAME_PLANET, lordDto.getPlanets().get(0).getName());
        assertEquals(lordDto, lordDto.getPlanets().get(0).getLord());
    }
}