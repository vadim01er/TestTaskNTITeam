package com.github.vadim01er.testtaskntiteam.controller;

import com.github.vadim01er.testtaskntiteam.entity.Lord;
import com.github.vadim01er.testtaskntiteam.entity.LordDTO;
import com.github.vadim01er.testtaskntiteam.entity.Planet;
import com.github.vadim01er.testtaskntiteam.entity.PlanetDTO;
import com.github.vadim01er.testtaskntiteam.exception.LordIsExistsException;
import com.github.vadim01er.testtaskntiteam.exception.LordNotFoundException;
import com.github.vadim01er.testtaskntiteam.exception.PlanetIsExistsException;
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
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/lords", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class LordController {

    private final LordServiceImpl lordServiceImpl;

    public LordController(LordServiceImpl lordServiceImpl) {
        this.lordServiceImpl = lordServiceImpl;
    }

    @GetMapping()
    public ResponseEntity<Object> getAll() {
        List<Lord> lords = lordServiceImpl.getAll();
        return ResponseEntity.ok(lords);
    }

    @GetMapping("/loafers")
    public ResponseEntity<Object> getLoafers() {
        List<Lord> lords = lordServiceImpl.getLoafers();
        return ResponseEntity.ok(lords);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) throws LordNotFoundException {
        Lord lord = lordServiceImpl.getById(id);
        return ResponseEntity.ok(lord);
    }

    @GetMapping("/top")
    public ResponseEntity<Object> getTop() {
        List<Lord> lords = lordServiceImpl.getTopTen();
        return ResponseEntity.ok(lords);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> post(@RequestBody LordDTO lordDTO) throws LordIsExistsException {
        Lord put = lordServiceImpl.put(lordDTO);
        return ResponseEntity.ok(put);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> putPlanet(@PathVariable("id") Long id, @Valid @RequestBody PlanetDTO planetDTO)
            throws LordNotFoundException, PlanetIsExistsException {
        Planet planet = lordServiceImpl.putPlanet(id, planetDTO);
        return ResponseEntity.ok(planet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) throws LordNotFoundException {
        lordServiceImpl.deleteById(id);
        return ResponseEntity.ok("ok");
    }
}
