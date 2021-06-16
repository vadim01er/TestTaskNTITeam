package com.github.vadim01er.testtaskntiteam.service;

import com.github.vadim01er.testtaskntiteam.dto.PlanetDto;
import com.github.vadim01er.testtaskntiteam.exception.PlanetNotFoundException;

import java.util.List;

/**
 * The interface Planet service.
 */
public interface PlanetService {

    /**
     * Gets all.
     *
     * @return the {@link List} of {@link PlanetDto}
     */
    List<PlanetDto> getAll();

    /**
     * Gets by id.
     *
     * @param id id ({@link Long}) of Planet
     * @return the {@link PlanetDto}
     * @throws PlanetNotFoundException the planet not found exception
     */
    PlanetDto getById(Long id) throws PlanetNotFoundException;

    /**
     * Add a new Planet without Lord.
     *
     * @param planetDto {@link PlanetDto}
     * @return the planet
     */
    PlanetDto add(PlanetDto planetDto);

    /**
     * Delete by id.
     *
     * @param id the id
     * @throws PlanetNotFoundException the planet not found exception
     */
    void deleteById(Long id) throws PlanetNotFoundException;

    /**
     * Update planet.
     *
     * @param id        the id
     * @param planetDto the {@link PlanetDto}
     * @return the {@link PlanetDto}
     * @throws PlanetNotFoundException the planet not found exception
     */
    PlanetDto update(Long id, PlanetDto planetDto) throws PlanetNotFoundException;

}
