package com.github.vadim01er.testtaskntiteam.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Collections;
import java.util.List;

/**
 * DTO class use for validation data for {@link Lord}.
 */
@Getter
@ToString
@Validated
@NoArgsConstructor
public class LordDto {
    @Null
    private Long id;

    @NotBlank
    @Length(min = 1, max = 250)
    private String name;

    @NotNull
    @Min(1)
    @Max(200)
    private Integer age;

    @Null
    @JsonIgnoreProperties({"lord"})
    private List<PlanetDto> planets;

    /**
     * Instantiates a new Lord dto.
     *
     * @param name the name
     * @param age  the age
     */
    public LordDto(String name, Integer age) {
        this.id = null;
        this.name = name;
        this.age = age;
        this.planets = null;
    }

    LordDto(Long id, String name, Integer age, List<PlanetDto> planets) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.planets = planets;
    }

    LordDto(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.planets = Collections.emptyList();
    }

}