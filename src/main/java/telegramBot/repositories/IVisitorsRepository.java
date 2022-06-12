package telegramBot.repositories;

import org.springframework.stereotype.Repository;
import telegramBot.model.Visitors;

import java.util.List;
import java.util.Optional;

/**
 * {@link Repository для работы с сущностью {@link Visitors}
 */
public interface IVisitorsRepository {

    Optional<Visitors> findVisitorByPhoneNumber(String phoneNumber);

    Optional<String> findTelephoneNumByChatId(Long chatId);

    Optional<Visitors> findVisitorByChatId(Long chatId);

    Optional<Long> findChatIdByPhoneNumber(String phoneNumber);

    List<Long> findAllChatId();

    boolean create(Visitors visitor);

    boolean updateByPhoneNumber(Visitors updateVisitors);

    boolean deleteVisitor(String phoneNumber);
}
