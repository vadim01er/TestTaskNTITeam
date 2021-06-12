package com.github.vadim01er.testtaskntiteam.service;

import com.github.vadim01er.testtaskntiteam.entity.Planet;
import com.github.vadim01er.testtaskntiteam.entity.PlanetDto;
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
    @MockBean
    private PlanetRepository planetRepository;

    @Test
    void getAll_Success() {
        when(planetRepository.findAll())
                .thenReturn(Collections.singletonList(Utils.getPlanet()));
        List<PlanetDto> planetDtoList = planetService.getAll();
        assertEquals(Collections.singletonList(Utils.getPlanetDto()), planetDtoList);
        when(planetRepository.findAll())
                .thenReturn(Collections.singletonList(Utils.getPlanetWithLord()));
        planetDtoList = planetService.getAll();
        assertEquals(Collections.singletonList(Utils.getPlanetDtoWithLordDto()), planetDtoList);
    }

    @Test
    void getById_Success() throws PlanetNotFoundException {
        when(planetRepository.findById(PLANET_ID))
                .thenReturn(Optional.of(Utils.getPlanet()));
        PlanetDto planetDto = planetService.getById(PLANET_ID);
        assertEquals(Utils.getPlanetDto(), planetDto);
        when(planetRepository.findById(PLANET_ID))
                .thenReturn(Optional.of(Utils.getPlanetWithLord()));
        planetDto = planetService.getById(PLANET_ID);
        assertEquals(Utils.getPlanetDtoWithLordDto(), planetDto);
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
                .thenReturn(Optional.of(Utils.getPlanet()));
        Planet planet = planetService.getPlanetById(PLANET_ID);
        assertEquals(Utils.getPlanet(), planet);
        when(planetRepository.findById(PLANET_ID))
                .thenReturn(Optional.of(Utils.getPlanetWithLord()));
        planet = planetService.getPlanetById(PLANET_ID);
        assertEquals(Utils.getPlanetWithLord(), planet);
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
                .thenReturn(Utils.getPlanet());
        Planet planet = planetService.save(Utils.getPlanet());
        assertEquals(Utils.getPlanet(), planet);
    }

    @Test
    void add_DefaultMethod_Success() {
        when(planetRepository.save(any(Planet.class)))
                .thenReturn(Utils.getPlanetWithLord());
        PlanetDto planetDto = planetService.add(Utils.getPlanetDto(), Utils.getLord());
        assertEquals(Utils.getPlanetDtoWithLordDto(), planetDto);
    }

    @Test
    void add_Success() {
        when(planetRepository.save(any(Planet.class)))
                .thenReturn(Utils.getPlanet());
        PlanetDto planetDto = planetService.add(Utils.getPlanetDto());
        assertEquals(Utils.getPlanetDto(), planetDto);
        when(planetRepository.save(any(Planet.class)))
                .thenReturn(Utils.getPlanetWithLord());
        planetDto = planetService.add(Utils.getPlanetDtoWithLordDto());
        assertEquals(Utils.getPlanetDtoWithLordDto(), planetDto);
    }

    @Test
    void update_Success() throws PlanetNotFoundException {
        when(planetRepository.findById(PLANET_ID))
                .thenReturn(Optional.of(Utils.getPlanet()));
        when(planetRepository.save(Utils.getPlanet()))
                .thenReturn(Utils.getPlanet());
        PlanetDto planetDto = planetService.update(PLANET_ID, Utils.getPlanetDto());
        assertEquals(Utils.getPlanetDto(), planetDto);
    }

    @Test
    void update_Exception_PlanetNotFound() {
        when(planetRepository.findById(PLANET_ID))
                .thenReturn(Optional.empty());
        assertThrows(PlanetNotFoundException.class,
                () -> planetService.update(PLANET_ID, Utils.getPlanetDto()));
    }

    @Test
    void deleteById() {
        doThrow(new EmptyResultDataAccessException(1))
                .when(planetRepository).deleteById(PLANET_ID);
        assertThrows(PlanetNotFoundException.class,
                () -> planetService.deleteById(PLANET_ID));
    }
}