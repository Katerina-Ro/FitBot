package telegramBot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    //@Query("SELECT v.telephoneNum FROM Visitors v WHERE v.chatId = :chatId")
    @Query(value = "SELECT pass_schema.visitors.tel_num FROM pass_schema.visitors " +
            "WHERE pass_schema.visitors.chat_id = :chatId", nativeQuery = true)
    Optional<String> findTelephoneNumByChatId(@Param("chatId")Long chatId);

    /*
    @Query("SELECT v.telephoneNum FROM Visitors v WHERE v.chatId = :chatId")
    Optional<String> isExistTelephoneNum(@Param("telephoneNum")String phoneNumber); */

    //@Query(name="SELECT tel_num FROM pass_schema.visitors WHERE chat_id = :chatId", nativeQuery = true)
    //@Query("SELECT v FROM Visitors v WHERE v.chatId = :chatId")
    @Query(value = "SELECT * FROM pass_schema.visitors WHERE pass_schema.visitors.chat_id = :chatId",
            nativeQuery = true)
    Optional<Visitors> findVisitorByChatId(@Param("chatId") Long chatId);

    //@Query("SELECT v FROM Visitors v WHERE v.telephoneNum = :telephoneNum")
    @Query(value = "SELECT pass_schema.visitors.chat_id FROM pass_schema.visitors " +
            "WHERE pass_schema.visitors.tel_num = :telephoneNum", nativeQuery = true)
    Optional<Long> findChatIdByPhoneNumber(@Param("telephoneNum") String phoneNumber);

    @Modifying
    @Query(value = "UPDATE pass_schema.visitors SET chat_id = :chatId WHERE tel_num = :phoneNumber",
            nativeQuery = true)
    void create(@Param("phoneNumber") String phoneNumber, @Param("chatId") Long chatId);
}
