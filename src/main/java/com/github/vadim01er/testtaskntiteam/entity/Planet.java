package com.github.vadim01er.testtaskntiteam.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.vadim01er.testtaskntiteam.dto.PlanetDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

/**
 * The type {@link Planet}.
 */
@Getter
@Setter
@Entity
@Table(name = "planets")
@NoArgsConstructor
public class Planet extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lord_id")
    @JsonIgnoreProperties({"planets"})
    private Lord lord;

    /**
     * Instantiates a new {@link Planet}.
     *
     * @param planetDto the {@link PlanetDto}
     * @param lord      the lord
     */
    public Planet(PlanetDto planetDto, Lord lord) {
        super(planetDto.getName());
        this.lord = lord;
    }

    /**
     * Update.
     *
     * @param planetDto the {@link PlanetDto}
     */
    public void update(PlanetDto planetDto) {
        super.setName(planetDto.getName());
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
        Planet planet = (Planet) o;
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
