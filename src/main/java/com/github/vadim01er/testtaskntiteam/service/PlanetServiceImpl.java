package com.github.vadim01er.testtaskntiteam.service;

import com.github.vadim01er.testtaskntiteam.dto.PlanetDto;
import com.github.vadim01er.testtaskntiteam.entity.Lord;
import com.github.vadim01er.testtaskntiteam.entity.Planet;
import com.github.vadim01er.testtaskntiteam.exception.PlanetNotFoundException;
import com.github.vadim01er.testtaskntiteam.mapper.PlanetMapper;
import com.github.vadim01er.testtaskntiteam.repository.PlanetRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class PlanetServiceImpl implements {@link PlanetService}.
 */
@Service
@AllArgsConstructor
public class PlanetServiceImpl implements PlanetService {

    private final PlanetRepository planetRepository;
    private final PlanetMapper planetMapper;

    @Override
    public List<PlanetDto> getAll() {
        return planetRepository.findAll().stream()
                .map(planetMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PlanetDto getById(Long id) throws PlanetNotFoundException {
        return planetRepository.findById(id)
                .map(planetMapper::toDto)
                .orElseThrow(() -> new PlanetNotFoundException(id));
    }

    /**
     * Gets {@link Planet} by id. (only in this package (default type method))
     *
     * @param id the the {@link Planet} id ({@link Long})
     * @return the {@link Planet} by id
     * @throws PlanetNotFoundException the planet not found exception
     */
    Planet getPlanetById(Long id) throws PlanetNotFoundException {
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
    PlanetDto add(PlanetDto planetDto, Lord lord) {
        return planetMapper.toDto(
                planetRepository.save(new Planet(planetDto, lord))
        );
    }

    @Override
    public PlanetDto add(PlanetDto planetDto) {
        return add(planetDto, null);
    }

    @Override
    public PlanetDto update(Long id, PlanetDto planetDto)
            throws PlanetNotFoundException {
        Planet planet = getPlanetById(id);
        planet.update(planetDto);
        return planetMapper.toDto(planetRepository.save(planet));
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
