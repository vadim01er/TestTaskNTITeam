package com.github.vadim01er.testtaskntiteam.mapper;

import com.github.vadim01er.testtaskntiteam.dto.PlanetDto;
import com.github.vadim01er.testtaskntiteam.entity.Planet;
import org.springframework.stereotype.Component;

@Component
public class PlanetMapper extends AbstractMapper<Planet, PlanetDto> {
    public PlanetMapper() {
        super(Planet.class, PlanetDto.class);
    }
}
