package com.github.vadim01er.testtaskntiteam.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

/**
 * DTO class use for validation data for {@link Planet}.
 */
@Getter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class PlanetDto {
    @Null
    private Long id;
    @NotBlank
    private String name;
    @Null
    @JsonIgnoreProperties({"planets"})
    private LordDto lord;
}
