package com.github.vadim01er.testtaskntiteam.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "Lord")
@NoArgsConstructor
public class Lord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;

    @OneToMany(mappedBy = "lord")
    private List<Planet> planets = new ArrayList<>();

    public Lord(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public LordDTO toDTO() {
        return new LordDTO(name, age,
                planets.stream().map(Planet::toDTO).collect(Collectors.toList()));
    }
}
