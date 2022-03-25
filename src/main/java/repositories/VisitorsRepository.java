package repositories;

import enteties.Visitors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * {@link Repository для работы с сущностью {@link enteties.Visitors}
 */
public interface VisitorsRepository extends JpaRepository<Visitors, Long> {

    //  получить номер телефона и фио всех, кто сегодня придет
    List<Visitors> findAllByCurrencyDay(LocalDate localDate);
}
