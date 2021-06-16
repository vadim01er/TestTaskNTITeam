package com.github.vadim01er.testtaskntiteam.service;

import com.github.vadim01er.testtaskntiteam.dto.LordDto;
import com.github.vadim01er.testtaskntiteam.dto.PlanetDto;
import com.github.vadim01er.testtaskntiteam.entity.Lord;
import com.github.vadim01er.testtaskntiteam.entity.Planet;
import com.github.vadim01er.testtaskntiteam.exception.LordNotFoundException;
import com.github.vadim01er.testtaskntiteam.exception.PlanetNotFoundException;
import com.github.vadim01er.testtaskntiteam.repository.LordRepository;
import com.github.vadim01er.testtaskntiteam.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.github.vadim01er.testtaskntiteam.utils.Utils.LORD_ID;
import static com.github.vadim01er.testtaskntiteam.utils.Utils.PLANET_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
class LordServiceImplUnitTest {

    @Autowired
    private LordServiceImpl lordService;
    @Autowired
    private Utils utils;

    @MockBean
    private LordRepository lordRepository;
    @MockBean
    private PlanetServiceImpl planetService;

    @Test
    void getById_Success() throws LordNotFoundException {
        when(lordRepository.findById(LORD_ID))
                .thenReturn(Optional.of(utils.getLord()));
        LordDto lordDto = lordService.getById(LORD_ID);
        assertEquals(utils.getLordDto(), lordDto);
        when(lordRepository.findById(LORD_ID))
                .thenReturn(Optional.of(utils.getLordWithPlanet()));
        lordDto = lordService.getById(LORD_ID);
        assertEquals(utils.getLordDtoWithPlanetDto(), lordDto);
    }

    @Test
    void getById_Exception_LordNotFound() {
        when(lordRepository.findById(LORD_ID))
                .thenReturn(Optional.empty());
        assertThrows(LordNotFoundException.class, () -> lordService.getById(LORD_ID));
    }

    @Test
    void getLordById_Success() throws LordNotFoundException {
        when(lordRepository.findById(LORD_ID))
                .thenReturn(Optional.of(utils.getLord()));
        Lord lord = lordService.getLordById(LORD_ID);
        assertEquals(utils.getLord(), lord);
        when(lordRepository.findById(LORD_ID))
                .thenReturn(Optional.of(utils.getLordWithPlanet()));
        lord = lordService.getLordById(LORD_ID);
        assertEquals(utils.getLordWithPlanet(), lord);
    }

    @Test
    void getLordById_Exception_LordNotFound() {
        when(lordRepository.findById(LORD_ID))
                .thenReturn(Optional.empty());
        assertThrows(LordNotFoundException.class, () -> lordService.getById(LORD_ID));
    }

    @Test
    void getTopTen_Success() {
        when(lordRepository.findTop10ByOrderByAge())
                .thenReturn(Collections.singletonList(utils.getLord()));
        List<LordDto> topTen = lordService.getTopTen();
        assertEquals(Collections.singletonList(utils.getLordDto()), topTen);
        when(lordRepository.findTop10ByOrderByAge())
                .thenReturn(Collections.singletonList(utils.getLordWithPlanet()));
        topTen = lordService.getTopTen();
        assertEquals(Collections.singletonList(utils.getLordDtoWithPlanetDto()), topTen);
    }

    @Test
    void getLoafers_Success() {
        when(lordRepository.findAllLoafers())
                .thenReturn(Collections.singletonList(utils.getLord()));
        List<LordDto> loafers = lordService.getLoafers();
        assertEquals(Collections.singletonList(utils.getLordDto()), loafers);
    }

    @Test
    void getAll_Success() {
        when(lordRepository.findAll())
                .thenReturn(Collections.singletonList(utils.getLord()));
        List<LordDto> lordDtoList = lordService.getAll();
        assertEquals(Collections.singletonList(utils.getLordDto()), lordDtoList);
        when(lordRepository.findAll())
                .thenReturn(Collections.singletonList(
                        utils.getLordWithPlanet()));
        lordDtoList = lordService.getAll();
        assertEquals(Collections.singletonList(utils.getLordDtoWithPlanetDto()), lordDtoList);
    }

    @Test
    void add_Success() {
        when(lordRepository.save(any(Lord.class)))
                .thenReturn(utils.getLord());
        LordDto lordDto = lordService.add(utils.getLordDto());
        assertEquals(utils.getLordDto(), lordDto);
    }

    @Test
    void addPlanet_Success() throws LordNotFoundException {
        when(lordRepository.findById(LORD_ID))
                .thenReturn(Optional.of(utils.getLord()));
        when(planetService.add(any(PlanetDto.class), eq(
                utils.getLord())))
                .thenReturn(utils.getPlanetDtoWithLordDto());
        PlanetDto planetDto = lordService.addPlanet(LORD_ID, utils.getPlanetDto());
        assertEquals(utils.getPlanetDtoWithLordDto(), planetDto);
    }

    @Test
    void addPlanet_Exception_LordNotFound() {
        when(lordRepository.findById(LORD_ID))
                .thenReturn(Optional.empty());
        assertThrows(LordNotFoundException.class,
                () -> lordService.addPlanet(LORD_ID, utils.getPlanetDto()));
    }

    @Test
    void assignToManagePlanet_Success() throws PlanetNotFoundException, LordNotFoundException {
        when(lordRepository.findById(LORD_ID))
                .thenReturn(Optional.of(utils.getLord()));
        when(planetService.getPlanetById(PLANET_ID))
                .thenReturn(utils.getPlanet());
        when(planetService.save(any(Planet.class)))
                .thenReturn(utils.getPlanetWithLord());
        PlanetDto planetDto = lordService.assignToManagePlanet(LORD_ID, PLANET_ID);
        assertEquals(utils.getPlanetDtoWithLordDto(), planetDto);
    }

    @Test
    void assignToManagePlanet_Exception_LordNotFound() {
        when(lordRepository.findById(LORD_ID))
                .thenReturn(Optional.empty());
        assertThrows(LordNotFoundException.class,
                () -> lordService.assignToManagePlanet(LORD_ID, PLANET_ID));
    }

    @Test
    void assignToManagePlanet_Exception_PlanetNotFound() throws PlanetNotFoundException {
        when(lordRepository.findById(LORD_ID))
                .thenReturn(Optional.of(
                        utils.getLord()));
        when(planetService.getPlanetById(PLANET_ID))
                .thenThrow(new PlanetNotFoundException(PLANET_ID));
        assertThrows(PlanetNotFoundException.class,
                () -> lordService.assignToManagePlanet(LORD_ID, PLANET_ID));
    }

    @Test
    void update_Success() throws LordNotFoundException {
        when(lordRepository.findById(LORD_ID))
                .thenReturn(Optional.of(
                        utils.getLord()));
        when(lordRepository.save(
                utils.getLord()))
                .thenReturn(
                        utils.getLord());
        LordDto lordDto = lordService.update(LORD_ID,
                utils.getLordDto());
        assertEquals(
                utils.getLordDto(), lordDto);
    }

    @Test
    void update_Exception_LordNotFound() {
        when(lordRepository.findById(LORD_ID))
                .thenReturn(Optional.empty());
        assertThrows(LordNotFoundException.class,
                () -> lordService.update(LORD_ID,
                        utils.getLordDto()));
    }

    @Test
    void deleteById_Exception_LordNotFound() {
        doThrow(new EmptyResultDataAccessException(1))
                .when(lordRepository).deleteById(LORD_ID);
        assertThrows(LordNotFoundException.class,
                () -> lordService.deleteById(LORD_ID));
    }
}