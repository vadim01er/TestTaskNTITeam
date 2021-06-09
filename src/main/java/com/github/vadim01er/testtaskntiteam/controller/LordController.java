package com.github.vadim01er.testtaskntiteam.controller;

import com.github.vadim01er.testtaskntiteam.entity.Lord;
import com.github.vadim01er.testtaskntiteam.entity.LordDto;
import com.github.vadim01er.testtaskntiteam.entity.Planet;
import com.github.vadim01er.testtaskntiteam.entity.PlanetDto;
import com.github.vadim01er.testtaskntiteam.exception.LordNotFoundException;
import com.github.vadim01er.testtaskntiteam.service.LordServiceImpl;
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
 * Controller for request mapping "api/v1/lords".
 */
@Validated
@RestController
@RequestMapping(value = "api/v1/lords", produces = MediaType.APPLICATION_JSON_VALUE)
public class LordController {

    private final LordServiceImpl lordServiceImpl;

    /**
     * Instantiates a new {@link Lord} controller.
     *
     * @param lordServiceImpl the {@link LordServiceImpl}
     */
    public LordController(LordServiceImpl lordServiceImpl) {
        this.lordServiceImpl = lordServiceImpl;
    }

    /**
     * Gets all.
     *
     * @return the {@link List} of all {@link Lord}
     */
    @GetMapping()
    public ResponseEntity<Object> getAll() {
        List<Lord> lords = lordServiceImpl.getAll();
        return ResponseEntity.ok(lords);
    }

    /**
     * Gets loafers.
     *
     * @return the {@link List} of {@link Lord} loafers (has no planet)
     */
    @GetMapping("/loafers")
    public ResponseEntity<Object> getLoafers() {
        List<Lord> lords = lordServiceImpl.getLoafers();
        return ResponseEntity.ok(lords);
    }

    /**
     * Gets by id.
     *
     * @param id the id ({@link Long}) of {@link Lord}
     * @return the {@link Lord}
     * @throws LordNotFoundException the lord not found exception
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(
            @Min(0) @PathVariable Long id
    ) throws LordNotFoundException {
        Lord lord = lordServiceImpl.getById(id);
        return ResponseEntity.ok(lord);
    }

    /**
     * Gets top.
     *
     * @return the {@link List} of top {@link Lord}
     */
    @GetMapping("/top")
    public ResponseEntity<Object> getTop() {
        List<Lord> lords = lordServiceImpl.getTopTen();
        return ResponseEntity.ok(lords);
    }

    /**
     * Create new {@link Lord}.
     *
     * @param lordDto the {@link LordDto}
     * @return the {@link Lord}
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> post(
            @Valid @RequestBody LordDto lordDto
    ) {
        Lord put = lordServiceImpl.add(lordDto);
        return ResponseEntity.ok(put);
    }

    /**
     * Create new {@link Planet} and link it to the {@link Lord}.
     *
     * @param id        the id ({@link Long}) of {@link Lord}
     * @param planetDto the {@link PlanetDto}
     * @return the {@link Planet}
     * @throws LordNotFoundException the lord not found exception
     */
    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addPlanet(
            @PathVariable("id") Long id,
            @Valid @RequestBody PlanetDto planetDto
    ) throws LordNotFoundException {
        Planet planet = lordServiceImpl.addPlanet(id, planetDto);
        return ResponseEntity.ok(planet);
    }

    /**
     * Update.
     *
     * @param id      the id ({@link Long}) of {@link Lord}
     * @param lordDto the {@link LordDto}
     * @return the {@link Lord}
     * @throws LordNotFoundException the lord not found exception
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody LordDto lordDto
    ) throws LordNotFoundException {
        Lord update = lordServiceImpl.update(id, lordDto);
        return ResponseEntity.ok(update);
    }

    /**
     * Delete by id.
     *
     * @param id the id ({@link Long}) of {@link Lord}
     * @return the status "ok" if all is success
     * @throws LordNotFoundException the lord not found exception
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(
            @PathVariable Long id
    ) throws LordNotFoundException {
        lordServiceImpl.deleteById(id);
        return ResponseEntity.ok("ok");
    }
}
