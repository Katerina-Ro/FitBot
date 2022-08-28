package telegramBot.repositories.impl;

import supportTable.DontComeToDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import telegramBot.repositories.IDontComeToDayRepository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class DontComeToDayRepository implements IDontComeToDayRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Value("insert into pass_schema.dont_come_today (chat_id, tel_num, surname, name, patronymic, currency_date)\n" +
            "values (:chatId, :telephoneNum, :surname, :name, :patronymic, :currencyDate)")
    private String insertDontComeToDay;

    @Autowired
    public DontComeToDayRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createDontComeToDay(DontComeToDay dontComeToDay) throws DataAccessException {
        Map<String, Object> paramMap = getParamMap(dontComeToDay);
        jdbcTemplate.update(insertDontComeToDay, paramMap);
    }

    private Map<String, Object> getParamMap(DontComeToDay dontComeToDay) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("chatId", dontComeToDay.getChatId());
        paramMap.put("telephoneNum", dontComeToDay.getTelephoneNum());
        paramMap.put("surname", dontComeToDay.getSurname());
        paramMap.put("name",dontComeToDay.getName());
        paramMap.put("patronymic", dontComeToDay.getPatronymic());
        paramMap.put("currencyDate", dontComeToDay.getCurrencyDate());
        return paramMap;
    }
}
