package com.github.vadim01er.testtaskntiteam.repository;

import com.github.vadim01er.testtaskntiteam.entity.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {
}
