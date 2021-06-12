package com.github.vadim01er.testtaskntiteam.service;

import com.github.vadim01er.testtaskntiteam.entity.Lord;
import com.github.vadim01er.testtaskntiteam.entity.LordDto;
import com.github.vadim01er.testtaskntiteam.entity.PlanetDto;
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

    @MockBean
    private LordRepository lordRepository;
    @MockBean
    private PlanetServiceImpl planetService;

    @Test
    void getById_Success() throws LordNotFoundException {
        when(lordRepository.findById(LORD_ID))
                .thenReturn(Optional.of(Utils.getLord()));
        LordDto lordDto = lordService.getById(LORD_ID);
        assertEquals(Utils.getLordDto(), lordDto);
        when(lordRepository.findById(LORD_ID))
                .thenReturn(Optional.of(Utils.getLordWithPlanet()));
        lordDto = lordService.getById(LORD_ID);
        assertEquals(Utils.getLordDtoWithPlanetDto(), lordDto);
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
                .thenReturn(Optional.of(Utils.getLord()));
        Lord lord = lordService.getLordById(LORD_ID);
        assertEquals(Utils.getLord(), lord);
        when(lordRepository.findById(LORD_ID))
                .thenReturn(Optional.of(Utils.getLordWithPlanet()));
        lord = lordService.getLordById(LORD_ID);
        assertEquals(Utils.getLordWithPlanet(), lord);
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
                .thenReturn(Collections.singletonList(Utils.getLord()));
        List<LordDto> topTen = lordService.getTopTen();
        assertEquals(Collections.singletonList(Utils.getLordDto()), topTen);
        when(lordRepository.findTop10ByOrderByAge())
                .thenReturn(Collections.singletonList(Utils.getLordWithPlanet()));
        topTen = lordService.getTopTen();
        assertEquals(Collections.singletonList(Utils.getLordDtoWithPlanetDto()), topTen);
    }

    @Test
    void getLoafers_Success() {
        when(lordRepository.findAllLoafers())
                .thenReturn(Collections.singletonList(Utils.getLord()));
        List<LordDto> loafers = lordService.getLoafers();
        assertEquals(Collections.singletonList(Utils.getLordDto()), loafers);
    }

    @Test
    void getAll_Success() {
        when(lordRepository.findAll())
                .thenReturn(Collections.singletonList(Utils.getLord()));
        List<LordDto> lordDtoList = lordService.getAll();
        assertEquals(Collections.singletonList(Utils.getLordDto()), lordDtoList);
        when(lordRepository.findAll())
                .thenReturn(Collections.singletonList(Utils.getLordWithPlanet()));
        lordDtoList = lordService.getAll();
        assertEquals(Collections.singletonList(Utils.getLordDtoWithPlanetDto()), lordDtoList);
    }

    @Test
    void add_Success() {
        when(lordRepository.save(any(Lord.class)))
                .thenReturn(Utils.getLord());
        LordDto lordDto = lordService.add(Utils.getLordDto());
        assertEquals(Utils.getLordDto(), lordDto);
    }

    @Test
    void addPlanet_Success() throws LordNotFoundException {
        when(lordRepository.findById(LORD_ID))
                .thenReturn(Optional.of(Utils.getLord()));
        when(planetService.add(any(PlanetDto.class), eq(Utils.getLord())))
                .thenReturn(Utils.getPlanetDtoWithLordDto());
        PlanetDto planetDto = lordService.addPlanet(LORD_ID, Utils.getPlanetDto());
        assertEquals(Utils.getPlanetDtoWithLordDto(), planetDto);
    }

    @Test
    void addPlanet_Exception_LordNotFound() {
        when(lordRepository.findById(LORD_ID))
                .thenReturn(Optional.empty());
        assertThrows(LordNotFoundException.class,
                () -> lordService.addPlanet(LORD_ID, Utils.getPlanetDto()));
    }

    @Test
    void assignToManagePlanet_Success() throws PlanetNotFoundException, LordNotFoundException {
        when(lordRepository.findById(LORD_ID))
                .thenReturn(Optional.of(Utils.getLord()));
        when(planetService.getPlanetById(PLANET_ID))
                .thenReturn(Utils.getPlanet());
        when(planetService.save(eq(Utils.getPlanet())))
                .thenReturn(Utils.getPlanetWithLord());
        PlanetDto planetDto = lordService.assignToManagePlanet(LORD_ID, PLANET_ID);
        assertEquals(Utils.getPlanetDtoWithLordDto(), planetDto);
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
                .thenReturn(Optional.of(Utils.getLord()));
        when(planetService.getPlanetById(PLANET_ID))
                .thenThrow(new PlanetNotFoundException(PLANET_ID));
        assertThrows(PlanetNotFoundException.class,
                () -> lordService.assignToManagePlanet(LORD_ID, PLANET_ID));
    }

    @Test
    void update_Success() throws LordNotFoundException {
        when(lordRepository.findById(LORD_ID))
                .thenReturn(Optional.of(Utils.getLord()));
        when(lordRepository.save(Utils.getLord()))
                .thenReturn(Utils.getLord());
        LordDto lordDto = lordService.update(LORD_ID, Utils.getLordDto());
        assertEquals(Utils.getLordDto(), lordDto);
    }

    @Test
    void update_Exception_LordNotFound() {
        when(lordRepository.findById(LORD_ID))
                .thenReturn(Optional.empty());
        assertThrows(LordNotFoundException.class,
                () -> lordService.update(LORD_ID, Utils.getLordDto()));
    }

    @Test
    void deleteById_Exception_LordNotFound() {
        doThrow(new EmptyResultDataAccessException(1))
                .when(lordRepository).deleteById(LORD_ID);
        assertThrows(LordNotFoundException.class,
                () -> lordService.deleteById(LORD_ID));
    }
}