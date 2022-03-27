package db.repositories;

import db.enteties.Visits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * {@link Repository для работы с сущностью {@link Visits }
 */
//@Repository
public interface VisitsRepository extends JpaRepository<Visits, Integer> {

    @Query("SELECT v From Visits v WHERE v.visits.dateVisit = :dateVisit")
    Optional<List<Integer>> findAllPassIdByCurrencyDay(@Param("dateVisit") LocalDate currencyDay);
}
