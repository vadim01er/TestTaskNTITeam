package com.github.vadim01er.testtaskntiteam.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.vadim01er.testtaskntiteam.entity.Planet;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Null;
import java.util.Objects;

/**
 * DTO class use for validation data for {@link Planet}.
 */
@Getter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class PlanetDto extends AbstractDto {

    @Schema(hidden = true)
    @Null
    @JsonIgnoreProperties({"planets"})
    private LordDto lord;

    public PlanetDto(String name) {
        super(null, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        PlanetDto planet = (PlanetDto) o;
        if (lord != null && planet.lord != null) {
            return Objects.equals(lord.getId(), planet.lord.getId());
        }
        return lord == null && planet.lord == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), lord.getId());
    }
}
