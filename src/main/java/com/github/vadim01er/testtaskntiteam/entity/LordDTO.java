package com.github.vadim01er.testtaskntiteam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LordDTO {
    private String name;
    private Integer age;
    private List<PlanetDTO> planetDTOS;
}
