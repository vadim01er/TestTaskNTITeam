package com.github.vadim01er.testtaskntiteam.service;

import com.github.vadim01er.testtaskntiteam.dto.LordDto;
import com.github.vadim01er.testtaskntiteam.dto.PlanetDto;
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
     * @param id the id ({@link Long}) of Lord
     * @return the {@link LordDto}
     * @throws LordNotFoundException the lord not found exception
     */
    LordDto getById(Long id) throws LordNotFoundException;

    /**
     * Gets top ten.
     *
     * @return the {@link List} of top ten {@link LordDto}
     */
    List<LordDto> getTopTen();

    /**
     * Gets loafers.
     *
     * @return the {@link List} of {@link LordDto} loafers (has no planet)
     */
    List<LordDto> getLoafers();

    /**
     * Gets all.
     *
     * @return the {@link List} of all {@link LordDto}
     */
    List<LordDto> getAll();

    /**
     * Add Lord.
     *
     * @param lordDto the {@link LordDto}
     * @return the {@link LordDto}
     */
    LordDto add(LordDto lordDto);

    /**
     * Add a new Planet and link it to the Lord.
     *
     * @param id        the id ({@link Long}) of Lord
     * @param planetDto the {@link PlanetDto}
     * @return the {@link PlanetDto}
     * @throws LordNotFoundException the lord not found exception
     */
    PlanetDto addPlanet(Long id, PlanetDto planetDto) throws LordNotFoundException;

    /**
     * Assign to manage planet dto.
     *
     * @param lordId   the id ({@link Long}) of Lord
     * @param planetId the {@link PlanetDto} id
     * @return the planet dto
     * @throws LordNotFoundException   the lord not found exception
     * @throws PlanetNotFoundException the planet not found exception
     */
    PlanetDto assignToManagePlanet(Long lordId, Long planetId)
            throws LordNotFoundException, PlanetNotFoundException;

    /**
     * Update lord.
     *
     * @param id      the id ({@link Long}) of Lord
     * @param lordDto the lord dto
     * @return the lord
     * @throws LordNotFoundException the lord not found exception
     */
    LordDto update(Long id, LordDto lordDto) throws LordNotFoundException;

    /**
     * Delete by id.
     *
     * @param id the id ({@link Long}) of Lord
     * @throws LordNotFoundException the lord not found exception
     */
    void deleteById(Long id) throws LordNotFoundException;

}
