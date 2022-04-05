package telegramBot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import telegramBot.enteties.Visitors;

import java.util.Optional;

/**
 * {@link Repository для работы с сущностью {@link Visitors }
 */
public interface VisitorsRepository extends JpaRepository<Visitors, Long> {

    @Query("SELECT v From Visitors v WHERE v.telephoneNum = :telephoneNum")
    Optional<Visitors> findVisitorByPhoneNumber(@Param("telephoneNum") String phoneNumber);

    @Query("SELECT v.telephoneNum From Visitors v WHERE v.telephoneNum = :telephoneNum")
    String findTelephoneNum(@Param("telephoneNum")String phoneNumber);
}
