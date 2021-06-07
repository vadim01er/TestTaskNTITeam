package com.github.vadim01er.testtaskntiteam.repository;

import com.github.vadim01er.testtaskntiteam.entity.Lord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LordRepository extends JpaRepository<Lord, Long> {

    /**
     * SQL Query: "SELECT * FROM Lords ORDERED BY age ASC LIMIT 0, 10"
     * @return Top 10 {@link Lord} in ascending order of age.
     */
    List<Lord> findTop10ByOrderByAge();
}
