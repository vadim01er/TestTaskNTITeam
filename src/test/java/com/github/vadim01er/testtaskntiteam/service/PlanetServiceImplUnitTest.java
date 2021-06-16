package com.github.vadim01er.testtaskntiteam.service;

import com.github.vadim01er.testtaskntiteam.dto.PlanetDto;
import com.github.vadim01er.testtaskntiteam.entity.Planet;
import com.github.vadim01er.testtaskntiteam.exception.PlanetNotFoundException;
import com.github.vadim01er.testtaskntiteam.repository.PlanetRepository;
import com.github.vadim01er.testtaskntiteam.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.github.vadim01er.testtaskntiteam.utils.Utils.PLANET_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
class PlanetServiceImplUnitTest {

    @Autowired
    private PlanetServiceImpl planetService;
    @Autowired
    private Utils utils;
    @MockBean
    private PlanetRepository planetRepository;

    @Test
    void getAll_Success() {
        when(planetRepository.findAll())
                .thenReturn(Collections.singletonList(utils.getPlanet()));
        List<PlanetDto> planetDtoList = planetService.getAll();
        assertEquals(Collections.singletonList(utils.getPlanetDto()), planetDtoList);
        when(planetRepository.findAll())
                .thenReturn(Collections.singletonList(utils.getPlanetWithLord()));
        planetDtoList = planetService.getAll();
        assertEquals(Collections.singletonList(utils.getPlanetDtoWithLordDto()), planetDtoList);
    }

    @Test
    void getById_Success() throws PlanetNotFoundException {
        when(planetRepository.findById(PLANET_ID))
                .thenReturn(Optional.of(utils.getPlanet()));
        PlanetDto planetDto = planetService.getById(PLANET_ID);
        assertEquals(utils.getPlanetDto(), planetDto);
        when(planetRepository.findById(PLANET_ID))
                .thenReturn(Optional.of(utils.getPlanetWithLord()));
        planetDto = planetService.getById(PLANET_ID);
        assertEquals(utils.getPlanetDtoWithLordDto(), planetDto);
    }

    @Test
    void getById_Exception_PlanetNotFound() {
        when(planetRepository.findById(PLANET_ID))
                .thenReturn(Optional.empty());
        assertThrows(PlanetNotFoundException.class, () -> planetService.getById(PLANET_ID));
    }

    @Test
    void getPlanetById_Success() throws PlanetNotFoundException {
        when(planetRepository.findById(PLANET_ID))
                .thenReturn(Optional.of(utils.getPlanet()));
        Planet planet = planetService.getPlanetById(PLANET_ID);
        assertEquals(utils.getPlanet(), planet);
        when(planetRepository.findById(PLANET_ID))
                .thenReturn(Optional.of(utils.getPlanetWithLord()));
        planet = planetService.getPlanetById(PLANET_ID);
        assertEquals(utils.getPlanetWithLord(), planet);
    }

    @Test
    void getPlanetById_Exception_PlanetNotFound() {
        when(planetRepository.findById(PLANET_ID))
                .thenReturn(Optional.empty());
        assertThrows(PlanetNotFoundException.class, () -> planetService.getPlanetById(PLANET_ID));
    }

    @Test
    void save_Success() {
        when(planetRepository.save(any(Planet.class)))
                .thenReturn(utils.getPlanet());
        Planet planet = planetService.save(utils.getPlanet());
        assertEquals(utils.getPlanet(), planet);
    }

    @Test
    void add_DefaultMethod_Success() {
        when(planetRepository.save(any(Planet.class)))
                .thenReturn(utils.getPlanetWithLord());
        PlanetDto planetDto = planetService.add(utils.getPlanetDto(), utils.getLord());
        assertEquals(utils.getPlanetDtoWithLordDto(), planetDto);
    }

    @Test
    void add_Success() {
        when(planetRepository.save(any(Planet.class)))
                .thenReturn(utils.getPlanet());
        PlanetDto planetDto = planetService.add(utils.getPlanetDto());
        assertEquals(utils.getPlanetDto(), planetDto);
        when(planetRepository.save(any(Planet.class)))
                .thenReturn(utils.getPlanetWithLord());
        planetDto = planetService.add(utils.getPlanetDtoWithLordDto());
        assertEquals(utils.getPlanetDtoWithLordDto(), planetDto);
    }

    @Test
    void update_Success() throws PlanetNotFoundException {
        when(planetRepository.findById(PLANET_ID))
                .thenReturn(Optional.of(utils.getPlanet()));
        when(planetRepository.save(utils.getPlanet()))
                .thenReturn(utils.getPlanet());
        PlanetDto planetDto = planetService.update(PLANET_ID, utils.getPlanetDto());
        assertEquals(utils.getPlanetDto(), planetDto);
    }

    @Test
    void update_Exception_PlanetNotFound() {
        when(planetRepository.findById(PLANET_ID))
                .thenReturn(Optional.empty());
        assertThrows(PlanetNotFoundException.class,
                () -> planetService.update(PLANET_ID, utils.getPlanetDto()));
    }

    @Test
    void deleteById() {
        doThrow(new EmptyResultDataAccessException(1))
                .when(planetRepository).deleteById(PLANET_ID);
        assertThrows(PlanetNotFoundException.class,
                () -> planetService.deleteById(PLANET_ID));
    }
}