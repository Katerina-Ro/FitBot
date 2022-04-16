package telegramBot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import telegramBot.enteties.Visitors;

import java.util.Optional;

/**
 * {@link Repository для работы с сущностью {@link Visitors}
 */
public interface VisitorsRepository extends JpaRepository<Visitors, String> {

    @Query("SELECT v FROM Visitors v WHERE v.telephoneNum = :telephoneNum")
    Optional<Visitors> findVisitorByPhoneNumber(@Param("telephoneNum") String phoneNumber);

    @Query("SELECT v.telephoneNum FROM Visitors v WHERE v.telephoneNum = :telephoneNum")
    String findTelephoneNum(@Param("telephoneNum")String phoneNumber);

    @Query(name="SELECT tel_num FROM pass_schema.visitors WHERE chat_id = :chatId", nativeQuery = true)
    //@Query("SELECT v.telephoneNum FROM Visitors v WHERE v.chatId = :chatId")
    Optional<String> findVisitorByChatId(@Param("chatId") String chatId);

    //@Query(name = "SELECT chat_id FROM visitors WHERE tel_num = :telephoneNum", nativeQuery = true)
    @Query("SELECT v FROM Visitors v WHERE v.telephoneNum = :telephoneNum")
    Optional<Visitors> findChatIdByPhoneNumber(@Param("telephoneNum") String phoneNumber);

    @Query("SELECT v.chatId FROM Visitors v WHERE v.telephoneNum = :telephoneNum")
    String findChatIDByPhoneNumber(@Param("telephoneNum") String phoneNumber);
}
