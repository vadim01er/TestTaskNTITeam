package com.github.vadim01er.testtaskntiteam.service;

import com.github.vadim01er.testtaskntiteam.entity.Lord;
import com.github.vadim01er.testtaskntiteam.entity.LordDto;
import com.github.vadim01er.testtaskntiteam.entity.Planet;
import com.github.vadim01er.testtaskntiteam.entity.PlanetDto;
import com.github.vadim01er.testtaskntiteam.exception.LordNotFoundException;
import com.github.vadim01er.testtaskntiteam.exception.PlanetNotFoundException;
import com.github.vadim01er.testtaskntiteam.repository.LordRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class LordServiceImpl implements {@link LordService}.
 */
@Service
@AllArgsConstructor
public class LordServiceImpl implements LordService {

    private final LordRepository lordRepository;
    private final PlanetServiceImpl planetService;

    @Override
    public Lord getById(Long id) throws LordNotFoundException {
        return lordRepository.findById(id)
                .orElseThrow(() -> new LordNotFoundException(id));
    }

    @Override
    public List<Lord> getTopTen() {
        return lordRepository.findTop10ByOrderByAge();
    }

    @Override
    public List<Lord> getLoafers() {
        return lordRepository.findAllLoafers();
    }

    @Override
    public List<Lord> getAll() {
        return lordRepository.findAll();
    }

    @Override
    public Lord add(LordDto lordDto) {
        return lordRepository.save(new Lord(lordDto));
    }

    @Override
    public Planet addPlanet(Long id, PlanetDto planetDto)
            throws LordNotFoundException {
        Lord lord = getById(id);
        return planetService.add(planetDto, lord);
    }

    @Override
    public Planet assignToManagePlanet(Long lordId, Long planetId)
            throws LordNotFoundException, PlanetNotFoundException {
        Lord lord = getById(lordId);
        Planet planet = planetService.getById(planetId);
        planet.setLord(lord);
        return planetService.save(planet);
    }

    @Override
    public Lord update(Long id, LordDto lordDto) throws LordNotFoundException {
        Lord lord = getById(id);
        lord.update(lordDto);
        return lordRepository.save(lord);
    }

    @Override
    public void deleteById(Long id) throws LordNotFoundException {
        try {
            lordRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new LordNotFoundException(id);
        }
    }
}
