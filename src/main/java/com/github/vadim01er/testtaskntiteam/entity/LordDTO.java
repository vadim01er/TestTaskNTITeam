package com.github.vadim01er.testtaskntiteam.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * DTO class use for validation data for {@link Lord}.
 */
@Getter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class LordDTO {
    @NotBlank
    private String name;
    @Min(1)
    @Max(200)
    private Integer age;
//    private List<PlanetDTO> planetDTOs;
}
