package com.github.vadim01er.testtaskntiteam.service;

import com.github.vadim01er.testtaskntiteam.entity.Lord;
import com.github.vadim01er.testtaskntiteam.entity.LordDto;
import com.github.vadim01er.testtaskntiteam.entity.Planet;
import com.github.vadim01er.testtaskntiteam.entity.PlanetDto;
import com.github.vadim01er.testtaskntiteam.exception.LordNotFoundException;
import com.github.vadim01er.testtaskntiteam.exception.PlanetNotFoundException;

import java.util.List;

/**
 * This is interface for LordService.
 */
public interface LordService {
    /**
     * Gets by id.
     *
     * @param id the id ({@link Long}) of {@link Lord}
     * @return the {@link Lord}
     * @throws LordNotFoundException the lord not found exception
     */
    Lord getById(Long id) throws LordNotFoundException;

    /**
     * Gets top ten.
     *
     * @return the {@link List} of top ten {@link Lord}
     */
    List<Lord> getTopTen();

    /**
     * Gets loafers.
     *
     * @return the {@link List} of {@link Lord} loafers (has no planet)
     */
    List<Lord> getLoafers();

    /**
     * Gets all.
     *
     * @return the {@link List} of all {@link Lord}
     */
    List<Lord> getAll();

    /**
     * Add {@link Lord}.
     *
     * @param lordDto the {@link LordDto}
     * @return the {@link Lord}
     */
    Lord add(LordDto lordDto);

    /**
     * Add a new {@link Planet} and link it to the {@link Lord}.
     *
     * @param id        the id ({@link Long}) of {@link Lord}
     * @param planetDto the {@link PlanetDto}
     * @return the {@link Planet}
     * @throws LordNotFoundException the lord not found exception
     */
    Planet addPlanet(Long id, PlanetDto planetDto) throws LordNotFoundException;

    Planet assignToManagePlanet(Long lordId, Long planetId)
            throws LordNotFoundException, PlanetNotFoundException;

    /**
     * Update lord.
     *
     * @param id  the id ({@link Long}) of {@link Lord}
     * @param lordDto the lord dto
     * @return the lord
     * @throws LordNotFoundException the lord not found exception
     */
    Lord update(Long id, LordDto lordDto) throws LordNotFoundException;

    /**
     * Delete by id.
     *
     * @param id the id ({@link Long}) of {@link Lord}
     * @throws LordNotFoundException the lord not found exception
     */
    void deleteById(Long id) throws LordNotFoundException;

}
