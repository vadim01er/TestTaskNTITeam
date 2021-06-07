package com.github.vadim01er.testtaskntiteam.service;

import com.github.vadim01er.testtaskntiteam.entity.Lord;
import com.github.vadim01er.testtaskntiteam.entity.LordDTO;
import com.github.vadim01er.testtaskntiteam.entity.Planet;
import com.github.vadim01er.testtaskntiteam.entity.PlanetDTO;
import com.github.vadim01er.testtaskntiteam.exception.LordIsExistsException;
import com.github.vadim01er.testtaskntiteam.exception.LordNotFoundException;
import com.github.vadim01er.testtaskntiteam.exception.PlanetIsExistsException;

import java.util.List;

public interface ILordService {
    LordDTO getById(Long id) throws LordNotFoundException;

    Lord put(LordDTO lordDTO) throws LordIsExistsException;

    void deleteById(Long id) throws LordNotFoundException;

    List<Lord> getAll();

    List<Lord> getTopTen();

    Planet putPlanet(Long id, PlanetDTO planetDTO) throws LordNotFoundException, PlanetIsExistsException;
}
