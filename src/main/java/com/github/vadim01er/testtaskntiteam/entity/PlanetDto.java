package com.github.vadim01er.testtaskntiteam.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

/**
 * DTO class use for validation data for {@link Planet}.
 */
@Getter
@Validated
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PlanetDto {
    @Null
    private Long id;
    @NotBlank
    @Length(min = 1, max = 250)
    private String name;
    @Null
    @JsonIgnoreProperties({"planets"})
    private LordDto lord;

    public PlanetDto(String name) {
        this.name = name;
    }
}
