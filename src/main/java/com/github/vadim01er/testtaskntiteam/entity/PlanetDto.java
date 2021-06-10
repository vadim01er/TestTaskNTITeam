package com.github.vadim01er.testtaskntiteam.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * DTO class use for validation data for {@link Planet}.
 */
@Getter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class PlanetDto {
    @NotBlank
    private String name;
}
