package telegramBot.repositories;

import supportTable.DontComeToDay;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

/**
 * {@link Repository для работы с сущностью {@link DontComeToDay }
 */
public interface IDontComeToDayRepository {
    void createDontComeToDay(DontComeToDay dontComeToDay) throws DataAccessException;
}
