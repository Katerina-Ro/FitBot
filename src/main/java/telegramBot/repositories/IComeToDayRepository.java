package telegramBot.repositories;

import supportTable.ComeToDay;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

/**
 * {@link Repository для работы с сущностью {@link ComeToDay}
 */
public interface IComeToDayRepository {
    void createComeToDay(ComeToDay comeToDay) throws DataAccessException;
}
