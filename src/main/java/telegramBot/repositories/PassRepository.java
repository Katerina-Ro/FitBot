package telegramBot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import telegramBot.enteties.Pass;

/**
 * {@link Repository для работы с сущностью {@link Pass }
 */
public interface PassRepository extends JpaRepository<Pass, Integer>{

    @Query("SELECT p.chatId From Pass p WHERE p.numPass = :numPass")
    Long findChatIdByPassId(@Param("numPass") Integer passId);
}
