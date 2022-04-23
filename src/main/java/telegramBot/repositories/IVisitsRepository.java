package telegramBot.repositories;

import org.springframework.stereotype.Repository;
import telegramBot.model.Visits;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * {@link Repository для работы с сущностью {@link Visits}
 */
public interface IVisitsRepository {

    Optional<List<Visits>> findAllPassBySpecifiedDay(Date specifiedDay);

    Optional<List<Visits>> findAllVisitsByPassId(Integer passId);

    boolean createVisit(Visits visits);

    boolean updateVisit(Visits visit);

    boolean deleteVisit(Integer passId);

    /*
    @Query(value = "SELECT * From pass_schema.visits WHERE dateVisit = :dateVisit", nativeQuery = true)
    Optional<List<Integer>> findAllPassIdByCurrencyDay(@Param("dateVisit") Date currencyDay); */
}
