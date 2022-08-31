package telegramBot.repositories;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import supportTable.ComeToDay;

import java.util.List;
import java.util.Optional;

/**
 * {@link Repository для работы с сущностью {@link ComeToDay}
 */
public interface IComeToDayRepository {
    void createComeToDay(ComeToDay comeToDay) throws DataAccessException;

    Optional<List<ComeToDay>> getComeToDay() throws DataAccessException;
}
