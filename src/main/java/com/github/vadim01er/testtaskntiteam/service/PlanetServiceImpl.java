package com.github.vadim01er.testtaskntiteam.service;

import com.github.vadim01er.testtaskntiteam.entity.Lord;
import com.github.vadim01er.testtaskntiteam.entity.Planet;
import com.github.vadim01er.testtaskntiteam.entity.PlanetDto;
import com.github.vadim01er.testtaskntiteam.exception.PlanetNotFoundException;
import com.github.vadim01er.testtaskntiteam.repository.PlanetRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class PlanetServiceImpl implements {@link PlanetService}.
 */
@Service
@AllArgsConstructor
public class PlanetServiceImpl implements PlanetService {

    private final PlanetRepository planetRepository;

    @Override
    public List<Planet> getAll() {
        return planetRepository.findAll();
    }

    @Override
    public Planet getById(Long id) throws PlanetNotFoundException {
        return planetRepository.findById(id)
                .orElseThrow(() -> new PlanetNotFoundException(id));
    }

    /**
     * Save or update {@link Planet}.
     *
     * @param planet the {@link Planet}
     * @return the {@link Planet}
     */
    Planet save(Planet planet) {
        return planetRepository.save(planet);
    }

    /**
     * Add a new {@link Planet} and link it to the {@link Lord}.
     *
     * @param planetDto the {@link PlanetDto}
     * @param lord      the {@link Lord}
     * @return the {@link Planet}
     */
    Planet add(PlanetDto planetDto, Lord lord) {
        return planetRepository.save(new Planet(planetDto, lord));
    }

    @Override
    public Planet add(PlanetDto planetDto) {
        return add(planetDto, null);
    }

    @Override
    public Planet update(Long id, PlanetDto planetDto)
            throws PlanetNotFoundException {
        Planet planet = getById(id);
        planet.update(planetDto);
        return planetRepository.save(planet);
    }

    @Override
    public void deleteById(Long id) throws PlanetNotFoundException {
        try {
            planetRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new PlanetNotFoundException(id);
        }
    }
}
