package com.github.vadim01er.testtaskntiteam.repository;

import com.github.vadim01er.testtaskntiteam.entity.Lord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * This is class LordRepository is Repository.
 * </p>
 * <p>
 * He extends {@link JpaRepository} with parameters
 * {@link Lord} (the domain type the repository manages)
 * and {@link Long} (the type of the id of the entity the repository manages)
 * </p>
 */
@Repository
public interface LordRepository extends JpaRepository<Lord, Long> {

    /**
     * SQL Query.
     * <p>
     * SELECT *
     * <br>FROM Lord
     * <br>ORDERED BY age ASC
     * <br>LIMIT 0, 10
     * </p>
     *
     * @return {@link List} of top 10 {@link Lord} in ascending order of age.
     */
    List<Lord> findTop10ByOrderByAge();

    /**
     * SQL {@link Query}.
     * <p>
     * SELECT DISTINCT lord
     * <br>FROM Lord lord
     * <br>LEFT JOIN Planet planet ON (lord.id = planet.lord.id)
     * <br>WHERE planet.lord IS NULL
     * </p>
     *
     * @return {@link List} of {@link Lord} who don't have a planet
     */
    @Query("SELECT DISTINCT lord FROM Lord lord "
            + "LEFT JOIN Planet planet ON (lord.id = planet.lord.id) WHERE planet.lord IS NULL")
    List<Lord> findAllLoafers();
}
