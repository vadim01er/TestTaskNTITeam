package com.github.vadim01er.testtaskntiteam.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * DTO class use for validation data for {@link Lord}.
 */
@Getter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class LordDto {
    @Schema(hidden = true)
    @Null
    private Long id;

    @Schema(maxLength = 250, minLength = 1)
    @NotBlank
    @Length(min = 1, max = 250)
    private String name;

    @NotNull
    @Min(1)
    @Max(200)
    private Integer age;

    @Schema(hidden = true)
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

    LordDto(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.planets = Collections.emptyList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LordDto lordDto = (LordDto) o;
        return Objects.equals(id, lordDto.id)
                && Objects.equals(name, lordDto.name)
                && Objects.equals(age, lordDto.age)
                && Objects.equals(planets, lordDto.planets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, planets);
    }
}
