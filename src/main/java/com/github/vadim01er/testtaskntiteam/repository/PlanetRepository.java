package com.github.vadim01er.testtaskntiteam.repository;

import com.github.vadim01er.testtaskntiteam.entity.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * This is class PlanetRepository is a Repository.
 * </p>
 * <p>
 * Extends {@link JpaRepository} with parameters
 * {@link Planet} (the domain type the repository manages)
 * and {@link Long} (the type of the id of the entity the repository manages)
 * </p>
 */
@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {
}
