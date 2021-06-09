package com.github.vadim01er.testtaskntiteam.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@ToString(exclude = "lord")
@EqualsAndHashCode
@Entity
@Table(name = "planets")
@NoArgsConstructor
public class Planet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lord_id")
    @JsonIgnoreProperties({"planets", "hibernateLazyInitializer"})
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
