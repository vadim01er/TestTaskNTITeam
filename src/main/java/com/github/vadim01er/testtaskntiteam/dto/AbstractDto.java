package com.github.vadim01er.testtaskntiteam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractDto implements Serializable {
    @Null
    @Schema(hidden = true)
    private Long id;

    @Schema(maxLength = 250, minLength = 1)
    @NotBlank
    @Length(min = 1, max = 250)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractDto that = (AbstractDto) o;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
