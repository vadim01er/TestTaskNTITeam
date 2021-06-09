package com.github.vadim01er.testtaskntiteam.service;

import com.github.vadim01er.testtaskntiteam.entity.Planet;
import com.github.vadim01er.testtaskntiteam.entity.PlanetDto;
import com.github.vadim01er.testtaskntiteam.exception.LordNotFoundException;
import com.github.vadim01er.testtaskntiteam.exception.PlanetNotFoundException;

import java.util.List;

/**
 * This is interface for PlanetService.
 */
public interface PlanetService {

    /**
     * Gets all.
     *
     * @return the {@link List} of {@link Planet}
     */
    List<Planet> getAll();

    /**
     * Gets by id.
     *
     * @param id id ({@link Long}) of {@link Planet}
     * @return the {@link Planet}
     * @throws PlanetNotFoundException the planet not found exception
     */
    Planet getById(Long id) throws PlanetNotFoundException;

    /**
     * Add a new {@link Planet} without Lord.
     *
     * @param planetDto {@link PlanetDto}
     * @return the planet
     */
    Planet add(PlanetDto planetDto);

    /**
     * Delete by id.
     *
     * @param id the id
     * @throws PlanetNotFoundException the planet not found exception
     */
    void deleteById(Long id) throws PlanetNotFoundException;

    /**
     * Link a planet to a lord.
     *
     * @param planetId the {@link Planet} id ({@link Long})
     * @param lordId   the Lord id ({@link Long})
     * @return the {@link Planet}
     * @throws PlanetNotFoundException the planet not found
     * @throws LordNotFoundException   the lord not found
     */
    Planet setLord(Long planetId, Long lordId)
            throws PlanetNotFoundException, LordNotFoundException;

    /**
     * Update planet.
     *
     * @param id        the id
     * @param planetDto the {@link PlanetDto}
     * @return the {@link Planet}
     * @throws PlanetNotFoundException the planet not found exception
     */
    Planet update(Long id, PlanetDto planetDto) throws PlanetNotFoundException;

}
