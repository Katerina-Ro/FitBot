package telegramBot.repositories;

import telegramBot.enteties.Visits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * {@link Repository для работы с сущностью {@link Visits }
 */
//@Repository
public interface VisitsRepository extends JpaRepository<Visits, Integer> {

    @Query(value = "SELECT * From visits WHERE dateVisit = :dateVisit", nativeQuery = true)
    // "SELECT v From Visits v WHERE v.visits.dateVisit = :dateVisit")
    Optional<List<Integer>> findAllPassIdByCurrencyDay(@Param("dateVisit") Date currencyDay);

/*
    @Query(value = "SELECT * From gift WHERE gift_owner_chat_id = :giftOwner AND gift_status_gift_owner = 'ACTIVE'" +
            "AND gift_status_giving = 'NOT_ACTIVE'", nativeQuery = true)
    List<Gift> findAllByGiftOwnerChatIdByStatusGift(@Param("giftOwner")Long chatIdUser);

    @Query("SELECT g From Gift g WHERE g.giftPresenter.chatId = :giftPresenter")
    List<Gift> findAllByGiftAnotherChatId(@Param("giftPresenter")Long chatIdUser); */

}
