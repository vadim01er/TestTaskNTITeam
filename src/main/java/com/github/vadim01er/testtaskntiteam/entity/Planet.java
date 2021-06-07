package com.github.vadim01er.testtaskntiteam.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "planets")
@NoArgsConstructor
public class Planet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lord_id"/*, nullable = false*/)
    private Lord lord;

    public Planet(String name, Lord lord) {
        this.name = name;
        this.lord = lord;
    }
    public PlanetDTO toDTO() {
        return new PlanetDTO(name);
    }

    public void update(PlanetDTO planetDTO) {
        this.name = planetDTO.getName();
    }
}
