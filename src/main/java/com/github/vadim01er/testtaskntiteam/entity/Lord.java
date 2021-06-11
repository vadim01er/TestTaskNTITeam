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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * The type {@link Lord}.
 */
@Getter
@Setter
@Entity
@Table(name = "Lord")
@EqualsAndHashCode
@NoArgsConstructor
public class Lord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "lord")
    @JsonIgnoreProperties({"lord"})
    private List<Planet> planets = new ArrayList<>();

    /**
     * Instantiates a new Lord.
     *
     * @param lordDto the {@link LordDto}
     */
    public Lord(LordDto lordDto) {
        this.name = lordDto.getName();
        this.age = lordDto.getAge();
    }

    /**
     * Update.
     *
     * @param lordDto the {@link LordDto}
     */
    public void update(LordDto lordDto) {
        this.name = lordDto.getName();
        this.age = lordDto.getAge();
    }

    /**
     * Convert to {@link LordDto}.
     *
     * @return the {@link LordDto}
     */
    public LordDto toDto() {
        List<PlanetDto> planetDtoList = new ArrayList<>(this.planets.size());
        LordDto lordDto = new LordDto(this.getId(), this.getName(), this.getAge(), planetDtoList);
        for (Planet planet : this.planets) {
            planetDtoList.add(new PlanetDto(planet.getId(), planet.getName(), lordDto));
        }
        return lordDto;
    }

}
