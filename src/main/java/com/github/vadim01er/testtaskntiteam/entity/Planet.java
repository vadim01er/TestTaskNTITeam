package com.github.vadim01er.testtaskntiteam.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The type {@link Planet}.
 */
@Getter
@Setter
@Entity
@Table(name = "planets")
@EqualsAndHashCode
@NoArgsConstructor
public class Planet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lord_id")
    @JsonIgnoreProperties({"planets"})
    private Lord lord;

    /**
     * Instantiates a new {@link Planet}.
     *
     * @param planetDto the {@link PlanetDto}
     */
    public Planet(PlanetDto planetDto, Lord lord) {
        this.name = planetDto.getName();
        this.lord = lord;
    }

    /**
     * Update.
     *
     * @param planetDto the {@link PlanetDto}
     */
    public void update(PlanetDto planetDto) {
        this.name = planetDto.getName();
    }

    /**
     * Convert to {@link PlanetDto}.
     *
     * @return the {@link PlanetDto}
     */
    public PlanetDto toDto() {
        LordDto lordDto =
                lord == null ? null : new LordDto(lord.getId(), lord.getName(), lord.getAge());
        return new PlanetDto(this.id, this.name, lordDto);
    }
}
