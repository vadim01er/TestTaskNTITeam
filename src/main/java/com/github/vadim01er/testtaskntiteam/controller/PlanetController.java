package com.github.vadim01er.testtaskntiteam.controller;

import com.github.vadim01er.testtaskntiteam.entity.Planet;
import com.github.vadim01er.testtaskntiteam.entity.PlanetDto;
import com.github.vadim01er.testtaskntiteam.exception.PlanetNotFoundException;
import com.github.vadim01er.testtaskntiteam.service.PlanetServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * Controller for request mapping "api/v1/planets".
 */
@Validated
@RestController
@RequestMapping(value = "api/v1/planets", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlanetController {

    private final PlanetServiceImpl planetServiceImpl;

    /**
     * Instantiates a new Planet controller.
     *
     * @param planetServiceImpl the planet service
     */
    public PlanetController(PlanetServiceImpl planetServiceImpl) {
        this.planetServiceImpl = planetServiceImpl;
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @GetMapping()
    public ResponseEntity<Object> getAll() {
        List<Planet> planets = planetServiceImpl.getAll();
        return ResponseEntity.ok(planets);
    }

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     * @throws PlanetNotFoundException the planet not found exception
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(
            @Min(0) @PathVariable Long id
    ) throws PlanetNotFoundException {
        Planet planet = planetServiceImpl.getById(id);
        return ResponseEntity.ok(planet);
    }

    /**
     * Create new {@link Planet}.
     *
     * @param planetDto the planet dto
     * @return the response entity
     */
    @PostMapping()
    public ResponseEntity<Object> add(
            @Valid @RequestBody PlanetDto planetDto
    ) {
        Planet add = planetServiceImpl.add(planetDto);
        return ResponseEntity.ok(add);
    }

    /**
     * Update.
     *
     * @param planetId  the planet id
     * @param planetDto the planet dto
     * @return the response entity
     * @throws PlanetNotFoundException the planet not found exception
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
            @Min(0) @PathVariable("id") Long planetId,
            @Valid @RequestBody PlanetDto planetDto
    ) throws PlanetNotFoundException {
        Planet update = planetServiceImpl.update(planetId, planetDto);
        return ResponseEntity.ok(update);
    }

    /**
     * Delete by id response entity.
     *
     * @param id the id
     * @return the response entity
     * @throws PlanetNotFoundException the planet not found exception
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(
            @Min(0) @PathVariable Long id
    ) throws PlanetNotFoundException {
        planetServiceImpl.deleteById(id);
        return ResponseEntity.ok("ok");
    }
}
