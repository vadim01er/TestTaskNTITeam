package com.github.vadim01er.testtaskntiteam.service;

import com.github.vadim01er.testtaskntiteam.entity.Planet;
import com.github.vadim01er.testtaskntiteam.entity.PlanetDTO;
import com.github.vadim01er.testtaskntiteam.exception.LordNotFoundException;
import com.github.vadim01er.testtaskntiteam.exception.PlanetIsExistsException;
import com.github.vadim01er.testtaskntiteam.exception.PlanetNotFoundException;

import java.util.List;

public interface IPlanetService {
    PlanetDTO getById(Long id) throws PlanetNotFoundException;
    Planet add(PlanetDTO planet) throws PlanetIsExistsException;
    void deleteById(Long id) throws PlanetNotFoundException;

    Planet setLord(Long planetId, Long lordId) throws PlanetNotFoundException, LordNotFoundException;
    Planet update(Long id, PlanetDTO planetDTO) throws PlanetNotFoundException;

    List<Planet> getAll();
}
