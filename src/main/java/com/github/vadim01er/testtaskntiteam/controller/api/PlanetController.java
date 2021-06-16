package com.github.vadim01er.testtaskntiteam.controller.api;

import com.github.vadim01er.testtaskntiteam.dto.PlanetDto;
import com.github.vadim01er.testtaskntiteam.entity.Planet;
import com.github.vadim01er.testtaskntiteam.exception.PlanetNotFoundException;
import com.github.vadim01er.testtaskntiteam.response.ExceptionResponse;
import com.github.vadim01er.testtaskntiteam.service.PlanetServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
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
     * @throws PlanetNotFoundException the planet not found exception
     */
    @Operation(summary = "Get all Planets")
    @ApiResponse(responseCode = "200", description = "Get all Planets",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PlanetDto.class)))})
    @ApiResponse(responseCode = "404", description = "Invalid Planet supplied",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))})
    @GetMapping()
    public ResponseEntity<Object> getAll() throws PlanetNotFoundException {
        List<PlanetDto> planets = planetServiceImpl.getAll();
        if (planets.isEmpty()) {
            throw new PlanetNotFoundException();
        }
        return ResponseEntity.ok(planets);
    }

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     * @throws PlanetNotFoundException the planet not found exception
     */
    @Operation(summary = "Get Planet by Id")
    @ApiResponse(responseCode = "200", description = "Get Planet by Id",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlanetDto.class))})
    @ApiResponse(responseCode = "404", description = "Invalid Planet supplied",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))})
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(
            @Min(0) @PathVariable Long id
    ) throws PlanetNotFoundException {
        PlanetDto planet = planetServiceImpl.getById(id);
        return ResponseEntity.ok(planet);
    }

    /**
     * Create new {@link Planet}.
     *
     * @param planetDto the planet dto
     * @return the response entity
     */
    @Operation(summary = "Create new Planet")
    @ApiResponse(responseCode = "201", description = "Create new Planet",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlanetDto.class))})
    @ApiResponse(responseCode = "400", description = "Invalid Planet supplied",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))})
    @PostMapping()
    public ResponseEntity<Object> add(
            @Valid @RequestBody PlanetDto planetDto
    ) {
        PlanetDto add = planetServiceImpl.add(planetDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(add);
    }

    /**
     * Update.
     *
     * @param planetId  the planet id
     * @param planetDto the planet dto
     * @return the response entity
     * @throws PlanetNotFoundException the planet not found exception
     */
    @Operation(summary = "Update Planet")
    @ApiResponse(responseCode = "201", description = "Update Planet",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlanetDto.class))})
    @ApiResponse(responseCode = "400", description = "Invalid Planet supplied",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))})
    @ApiResponse(responseCode = "404", description = "Not found Planet",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))})
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
            @Min(0) @PathVariable("id") Long planetId,
            @Valid @RequestBody PlanetDto planetDto
    ) throws PlanetNotFoundException {
        PlanetDto update = planetServiceImpl.update(planetId, planetDto);
        return ResponseEntity.ok(update);
    }

    /**
     * Delete by id response entity.
     *
     * @param id the id
     * @return the response entity
     * @throws PlanetNotFoundException the planet not found exception
     */
    @Operation(summary = "Delete Planet by id")
    @ApiResponse(responseCode = "204", description = "Delete Planet by id",
            content = @Content(schema = @Schema(allowableValues = "{\"status\": 204}")))
    @ApiResponse(responseCode = "404", description = "Not found planet",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))})
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(
            @Min(0) @PathVariable Long id
    ) throws PlanetNotFoundException {
        planetServiceImpl.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("{\"status\": 204}");
    }
}
