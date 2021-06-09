package com.github.vadim01er.testtaskntiteam.service;

import com.github.vadim01er.testtaskntiteam.entity.Lord;
import com.github.vadim01er.testtaskntiteam.entity.Planet;
import com.github.vadim01er.testtaskntiteam.entity.PlanetDTO;
import com.github.vadim01er.testtaskntiteam.exception.LordNotFoundException;
import com.github.vadim01er.testtaskntiteam.exception.PlanetIsExistsException;
import com.github.vadim01er.testtaskntiteam.exception.PlanetNotFoundException;
import com.github.vadim01er.testtaskntiteam.repository.LordRepository;
import com.github.vadim01er.testtaskntiteam.repository.PlanetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class PlanetServiceImpl implements PlanetService {

    private final PlanetRepository planetRepository;
    private final LordRepository lordRepository;

    @Override
    public List<Planet> getAll() {
        return planetRepository.findAll();
    }

    @Override
    public Planet getById(Long id) throws PlanetNotFoundException {
        try {
            return planetRepository.getById(id);
        } catch (EntityNotFoundException ex) {
            throw new PlanetNotFoundException(id);
        }
    }

    @Override
    public Planet add(PlanetDTO planet) throws PlanetIsExistsException {
        Planet save;
        try {
            save = planetRepository.save(new Planet(planet.getName(), null));
        } catch (IllegalArgumentException ex) {
            throw new PlanetIsExistsException();
        }
        return save;
    }

    @Override
    public void deleteById(Long id) throws PlanetNotFoundException {
        try {
            planetRepository.deleteById(id);
        } catch (IllegalArgumentException ex) {
            throw new PlanetNotFoundException(id);
        }
    }

    @Override
    public Planet setLord(Long planetId, Long lordId) throws PlanetNotFoundException, LordNotFoundException {
        Lord lord;
        try {
            lord = lordRepository.getById(lordId);
        } catch (EntityNotFoundException ex) {
            throw new LordNotFoundException(lordId);
        }
        Planet planet;
        try {
            planet = planetRepository.getById(planetId);
        } catch (EntityNotFoundException ex) {
            throw new PlanetNotFoundException(planetId);
        }
        planet.setLord(lord);
        return planetRepository.save(planet);
    }

    @Override
    public Planet update(Long id, PlanetDTO planetDTO) throws PlanetNotFoundException {
        try {
            Planet planet = planetRepository.getById(id);
            planet.update(planetDTO);
            return planetRepository.save(planet);
        } catch (EntityNotFoundException ex) {
            throw new PlanetNotFoundException(id);
        }
    }
}
