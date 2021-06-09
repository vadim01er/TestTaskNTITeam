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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "Lord")
@NoArgsConstructor
public class Lord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "lord")
    @JsonIgnoreProperties("lord")
    private List<Planet> planets = new ArrayList<>();

    public Lord(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

}
