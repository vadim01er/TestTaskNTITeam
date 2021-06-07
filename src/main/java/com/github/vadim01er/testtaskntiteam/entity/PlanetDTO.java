package com.github.vadim01er.testtaskntiteam.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class PlanetDTO {
    @NotBlank
    private String name;
}
