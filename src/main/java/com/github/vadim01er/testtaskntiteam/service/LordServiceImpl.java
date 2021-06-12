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
import java.util.stream.Collectors;

/**
 * This class LordServiceImpl implements {@link LordService}.
 */
@Service
@AllArgsConstructor
public class LordServiceImpl implements LordService {

    private final LordRepository lordRepository;
    private final PlanetServiceImpl planetService;

    @Override
    public LordDto getById(Long id) throws LordNotFoundException {
        return getLordById(id).toDto();
    }

    public Lord getLordById(Long id) throws LordNotFoundException {
        return lordRepository.findById(id)
                .orElseThrow(() -> new LordNotFoundException(id));
    }

    @Override
    public List<LordDto> getTopTen() {
        return lordRepository.findTop10ByOrderByAge().stream()
                .map(Lord::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LordDto> getLoafers() {
        return lordRepository.findAllLoafers().stream()
                .map(Lord::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LordDto> getAll() {
        return lordRepository.findAll().stream()
                .map(Lord::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LordDto add(LordDto lordDto) {
        return lordRepository.save(new Lord(lordDto))
                .toDto();
    }

    @Override
    public PlanetDto addPlanet(Long id, PlanetDto planetDto)
            throws LordNotFoundException {
        Lord lord = getLordById(id);
        return planetService.add(planetDto, lord);
    }

    @Override
    public PlanetDto assignToManagePlanet(Long lordId, Long planetId)
            throws LordNotFoundException, PlanetNotFoundException {
        Lord lord = getLordById(lordId);
        Planet planet = planetService.getPlanetById(planetId);
        planet.setLord(lord);
        return planetService.save(planet)
                .toDto();
    }

    @Override
    public LordDto update(Long id, LordDto lordDto) throws LordNotFoundException {
        Lord lord = getLordById(id);
        lord.update(lordDto);
        return lordRepository.save(lord)
                .toDto();
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
