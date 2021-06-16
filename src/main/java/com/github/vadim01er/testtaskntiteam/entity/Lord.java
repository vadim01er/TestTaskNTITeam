package com.github.vadim01er.testtaskntiteam.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.vadim01er.testtaskntiteam.dto.LordDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type {@link Lord}.
 */
@Getter
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "Lord")
@NoArgsConstructor
public class Lord extends AbstractEntity {

    private Integer age;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "lord")
    @JsonIgnoreProperties({"lord"})
    private List<Planet> planets = new ArrayList<>();

    /**
     * Instantiates a new Lord.
     *
     * @param lordDto the {@link LordDto}
     */
    public Lord(LordDto lordDto) {
        super(lordDto.getName());
        this.age = lordDto.getAge();
    }

    /**
     * Update.
     *
     * @param lordDto the {@link LordDto}
     */
    public void update(LordDto lordDto) {
        super.setName(lordDto.getName());
        this.age = lordDto.getAge();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Lord lord = (Lord) o;
        return Objects.equals(age, lord.age)
                && Objects.equals(planets, lord.planets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), age, planets);
    }
}
