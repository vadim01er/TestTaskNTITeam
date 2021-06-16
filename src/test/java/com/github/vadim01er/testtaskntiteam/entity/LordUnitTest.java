package com.github.vadim01er.testtaskntiteam.entity;

import com.github.vadim01er.testtaskntiteam.dto.LordDto;
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

}