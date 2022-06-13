package telegramBot.repositories.impl;

import supportTable.ComeToDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import telegramBot.repositories.IComeToDayRepository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ComeToDayRepository implements IComeToDayRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Value("insert ")
    private String insertComeToDay;

    @Autowired
    public ComeToDayRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void createComeToDay(ComeToDay comeToDay) throws DataAccessException {
        Map<String, Object> paramMap = getParamMap(comeToDay);
        jdbcTemplate.update(insertComeToDay, paramMap);
    }

    private Map<String, Object> getParamMap(ComeToDay comeToDay) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("chatId", comeToDay.getChatId());
        paramMap.put("telephoneNum", comeToDay.getTelephoneNum());
        paramMap.put("surname", comeToDay.getSurname());
        paramMap.put("name",comeToDay.getName());
        paramMap.put("patronymic", comeToDay.getPatronymic());
        paramMap.put("currencyDate", comeToDay.getCurrencyDate());
        return paramMap;
    }
}
