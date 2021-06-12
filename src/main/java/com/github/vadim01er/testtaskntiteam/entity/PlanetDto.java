package com.github.vadim01er.testtaskntiteam.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.Objects;

/**
 * DTO class use for validation data for {@link Planet}.
 */
@Getter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class PlanetDto {
    @Schema(hidden = true)
    @Null
    private Long id;

    @Schema(minLength = 1, maxLength = 250)
    @NotBlank
    @Length(min = 1, max = 250)
    private String name;

    @Schema(hidden = true)
    @Null
    @JsonIgnoreProperties({"planets"})
    private LordDto lord;

    public PlanetDto(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PlanetDto planetDto = (PlanetDto) o;
        boolean b = Objects.equals(id, planetDto.id) && Objects.equals(name, planetDto.name);
        if (b && lord == planetDto.lord) {
            return true;
        }
        return b && Objects.equals(lord.getId(), planetDto.lord.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lord);
    }
}
