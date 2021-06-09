package com.github.vadim01er.testtaskntiteam.service;

import com.github.vadim01er.testtaskntiteam.entity.Lord;
import com.github.vadim01er.testtaskntiteam.entity.LordDTO;
import com.github.vadim01er.testtaskntiteam.entity.Planet;
import com.github.vadim01er.testtaskntiteam.entity.PlanetDTO;
import com.github.vadim01er.testtaskntiteam.exception.LordIsExistsException;
import com.github.vadim01er.testtaskntiteam.exception.LordNotFoundException;
import com.github.vadim01er.testtaskntiteam.exception.PlanetIsExistsException;
import com.github.vadim01er.testtaskntiteam.repository.LordRepository;
import com.github.vadim01er.testtaskntiteam.repository.PlanetRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * This class LordServiceImpl implements {@link LordService}.
 */
@Service
@AllArgsConstructor
public class LordServiceImpl implements LordService {

    private final LordRepository lordRepository;
    private final PlanetRepository planetRepository;

    @Override
    public Lord getById(Long id) throws LordNotFoundException {
        try {
            return lordRepository.getById(id);
        } catch (EntityNotFoundException ex) {
            throw new LordNotFoundException(id);
        }
    }

    @Override
    public List<Lord> getLoafers() {
        return lordRepository.findAllLoafers();
    }

    @Override
    public Lord put(LordDTO lordDTO) throws LordIsExistsException {
        Lord save;
        try {
            save = lordRepository.save(new Lord(lordDTO.getName(), lordDTO.getAge()));
        } catch (IllegalArgumentException ex) {
            throw new LordIsExistsException();
        }
        return save;
    }

    @Override
    public void deleteById(Long id) throws LordNotFoundException {
        try {
            lordRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new LordNotFoundException(id);
        }
    }

    @Override
    public List<Lord> getAll() {
        return lordRepository.findAll();
    }

    @Override
    public List<Lord> getTopTen() {
        return lordRepository.findTop10ByOrderByAge();
    }

    @Override
    public Planet putPlanet(Long id, PlanetDTO planetDTO)
            throws LordNotFoundException, PlanetIsExistsException {
        try {
            Lord lord = lordRepository.getById(id);
            return planetRepository.save(new Planet(planetDTO.getName(), lord));
        } catch (EntityNotFoundException ex) {
            throw new LordNotFoundException(id);
        } catch (IllegalArgumentException ex) {
            throw new PlanetIsExistsException();
        }
    }
}
