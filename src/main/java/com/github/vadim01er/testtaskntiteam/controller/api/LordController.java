package com.github.vadim01er.testtaskntiteam.controller.api;

import com.github.vadim01er.testtaskntiteam.dto.LordDto;
import com.github.vadim01er.testtaskntiteam.dto.PlanetDto;
import com.github.vadim01er.testtaskntiteam.entity.Lord;
import com.github.vadim01er.testtaskntiteam.entity.Planet;
import com.github.vadim01er.testtaskntiteam.exception.LordNotFoundException;
import com.github.vadim01er.testtaskntiteam.exception.PlanetNotFoundException;
import com.github.vadim01er.testtaskntiteam.response.ExceptionResponse;
import com.github.vadim01er.testtaskntiteam.service.LordServiceImpl;
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
import org.springframework.web.bind.annotation.RequestParam;
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
     * @throws LordNotFoundException the lord not found exception
     */
    @Operation(summary = "Get all Lords")
    @ApiResponse(responseCode = "200", description = "Get all Lords",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = LordDto.class)))})
    @ApiResponse(responseCode = "404", description = "Invalid Lord supplied",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))})
    @GetMapping()
    public ResponseEntity<Object> getAll()
            throws LordNotFoundException {
        List<LordDto> lords = lordServiceImpl.getAll();
        if (lords.isEmpty()) {
            throw new LordNotFoundException();
        }
        return ResponseEntity.ok(lords);
    }

    /**
     * Gets loafers.
     *
     * @return the {@link List} of {@link Lord} loafers(has no planet)
     * @throws LordNotFoundException the lord not found exception
     */
    @Operation(summary = "Get loafer Lords")
    @ApiResponse(responseCode = "200", description = "Get loafer Lords",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = LordDto.class)))})
    @ApiResponse(responseCode = "404", description = "Not found lord",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))})
    @GetMapping("/loafers")
    public ResponseEntity<Object> getLoafers()
            throws LordNotFoundException {
        List<LordDto> lords = lordServiceImpl.getLoafers();
        if (lords.isEmpty()) {
            throw new LordNotFoundException();
        }
        return ResponseEntity.ok(lords);
    }

    /**
     * Gets by id.
     *
     * @param id the id ({@link Long}) of {@link Lord}
     * @return the {@link LordDto}
     * @throws LordNotFoundException the lord not found exception
     */
    @Operation(summary = "Get Lord by id")
    @ApiResponse(responseCode = "200", description = "Get by id Lord",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = LordDto.class))})
    @ApiResponse(responseCode = "404", description = "Not found lord",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))})
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(
            @Min(0) @PathVariable Long id
    ) throws LordNotFoundException {
        LordDto lord = lordServiceImpl.getById(id);
        return ResponseEntity.ok(lord);
    }

    /**
     * Gets top.
     *
     * @return the {@link List} of top {@link LordDto}
     * @throws LordNotFoundException the lord not found exception
     */
    @Operation(summary = "Get top 10 Lords by age")
    @ApiResponse(responseCode = "200", description = "Get top 10 Lords",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = LordDto.class)))})
    @ApiResponse(responseCode = "404", description = "Not found lord",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))})
    @GetMapping("/top")
    public ResponseEntity<Object> getTop()
            throws LordNotFoundException {
        List<LordDto> lords = lordServiceImpl.getTopTen();
        if (lords.isEmpty()) {
            throw new LordNotFoundException();
        }
        return ResponseEntity.ok(lords);
    }

    /**
     * Create new {@link Lord}.
     *
     * @param lordDto the {@link LordDto}
     * @return the {@link Lord}
     */
    @Operation(summary = "Create new Lord")
    @ApiResponse(responseCode = "201", description = "Create new Lord",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = LordDto.class))})
    @ApiResponse(responseCode = "400", description = "Invalid Lord supplied",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))})
    @PostMapping()
    public ResponseEntity<Object> post(
            @Valid @RequestBody LordDto lordDto
    ) {
        LordDto put = lordServiceImpl.add(lordDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(put);
    }

    /**
     * Create new {@link Planet} and link it to the {@link Lord}.
     *
     * @param id        the id ({@link Long}) of {@link Lord}
     * @param planetDto the {@link PlanetDto}
     * @return the {@link Planet}
     * @throws LordNotFoundException the lord not found exception
     */
    @Operation(summary = "Create new Planet assigned to Lord")
    @ApiResponse(responseCode = "201", description = "Create new Planet assigned to Lord",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = LordDto.class))})
    @ApiResponse(responseCode = "400", description = "Invalid Planet supplied",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))})
    @ApiResponse(responseCode = "404", description = "Not found lord",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))})
    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addPlanet(
            @Min(0) @PathVariable("id") Long id,
            @Valid @RequestBody PlanetDto planetDto
    ) throws LordNotFoundException {
        PlanetDto planet = lordServiceImpl.addPlanet(id, planetDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(planet);
    }

    /**
     * Assign {@link Planet} to the {@link Lord}.
     *
     * @param lordId   the {@link Lord} id ({@link Long})
     * @param planetId the {@link Planet} id ({@link Long})
     * @return the response entity
     * @throws LordNotFoundException   the lord not found exception
     * @throws PlanetNotFoundException the planet not found exception
     */
    @Operation(summary = "Assign Planet to the Lord")
    @ApiResponse(responseCode = "200", description = "Assign Planet to the Lord",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = LordDto.class))})
    @ApiResponse(responseCode = "404", description = "Not found Lord or Planet",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))})
    @PutMapping("/{id}/assign_planet")
    public ResponseEntity<Object> assignPlanetToLord(
            @Min(0) @PathVariable("id") Long lordId,
            @Min(0) @RequestParam("planet_id") Long planetId
    ) throws LordNotFoundException, PlanetNotFoundException {
        PlanetDto update = lordServiceImpl.assignToManagePlanet(lordId, planetId);
        return ResponseEntity.ok(update);
    }

    /**
     * Update.
     *
     * @param id      the id ({@link Long}) of {@link Lord}
     * @param lordDto the {@link LordDto}
     * @return the {@link Lord}
     * @throws LordNotFoundException the lord not found exception
     */
    @Operation(summary = "Update Lord")
    @ApiResponse(responseCode = "200", description = "Update Lord",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = LordDto.class))})
    @ApiResponse(responseCode = "400", description = "Invalid Lord supplied",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))})
    @ApiResponse(responseCode = "404", description = "Not found lord",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))})
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(
            @Min(0) @PathVariable("id") Long id,
            @Valid @RequestBody LordDto lordDto
    ) throws LordNotFoundException {
        LordDto update = lordServiceImpl.update(id, lordDto);
        return ResponseEntity.ok(update);
    }

    /**
     * Delete by id.
     *
     * @param id the id ({@link Long}) of {@link Lord}
     * @return the status "ok" if all is success
     * @throws LordNotFoundException the lord not found exception
     */
    @Operation(summary = "Delete Lord by id")
    @ApiResponse(responseCode = "204", description = "Delete Lord by id")
    @ApiResponse(responseCode = "404", description = "Not found lord",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))})
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(
            @Min(0) @PathVariable Long id
    ) throws LordNotFoundException {
        lordServiceImpl.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("{\"status\": 204}");
    }
}
