package telegramBot.repositories;

import telegramBot.enteties.Visits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * {@link Repository для работы с сущностью {@link Visits }
 */
public interface VisitsRepository extends JpaRepository<Visits, Integer> {

    @Query(value = "SELECT * From visits WHERE dateVisit = :dateVisit", nativeQuery = true)
    Optional<List<Integer>> findAllPassIdByCurrencyDay(@Param("dateVisit") Date currencyDay);
}
