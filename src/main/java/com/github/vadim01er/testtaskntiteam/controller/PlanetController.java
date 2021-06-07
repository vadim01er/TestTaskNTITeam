package com.github.vadim01er.testtaskntiteam.controller;

import com.github.vadim01er.testtaskntiteam.entity.Planet;
import com.github.vadim01er.testtaskntiteam.entity.PlanetDTO;
import com.github.vadim01er.testtaskntiteam.exception.LordNotFoundException;
import com.github.vadim01er.testtaskntiteam.exception.PlanetIsExistsException;
import com.github.vadim01er.testtaskntiteam.exception.PlanetNotFoundException;
import com.github.vadim01er.testtaskntiteam.service.PlanetService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "api/v1/planets", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class PlanetController {

    private final PlanetService planetService;

    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @GetMapping()
    public ResponseEntity<Object> getAll() {
        List<Planet> planets = planetService.getAll();
        return ResponseEntity.ok(planets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) throws PlanetNotFoundException {
        PlanetDTO planetDTO = planetService.getById(id);
        return ResponseEntity.ok(planetDTO);
    }

    @PostMapping()
    public ResponseEntity<Object> add(@RequestBody PlanetDTO planetDTO)throws PlanetIsExistsException {
        Planet add = planetService.add(planetDTO);
        return ResponseEntity.ok(add);
    }

    @PutMapping("/{id}/update_lord")
    public ResponseEntity<Object> updateLord(@PathVariable("id") Long planetId, @RequestParam("lord_id") Long lordId)
            throws LordNotFoundException, PlanetNotFoundException {
        Planet update = planetService.setLord(planetId, lordId);
        return ResponseEntity.ok(update);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long planetId, @RequestBody PlanetDTO planetDTO)
            throws PlanetNotFoundException {
        Planet update = planetService.update(planetId, planetDTO);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) throws PlanetNotFoundException {
        planetService.deleteById(id);
        return ResponseEntity.ok("ok");
    }
}
