package telegramBot.repositories;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import supportTable.DontComeToDay;

import java.util.List;
import java.util.Optional;

/**
 * {@link Repository для работы с сущностью {@link DontComeToDay }
 */
public interface IDontComeToDayRepository {
    void createDontComeToDay(DontComeToDay dontComeToDay) throws DataAccessException;

    Optional<List<DontComeToDay>> getDontComeToDay() throws DataAccessException;
}
