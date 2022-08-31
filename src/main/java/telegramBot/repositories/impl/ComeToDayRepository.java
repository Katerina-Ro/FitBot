package telegramBot.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import supportTable.ComeToDay;
import telegramBot.repositories.IComeToDayRepository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ComeToDayRepository implements IComeToDayRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Value("insert into pass_schema.come_today (chat_id, tel_num, surname, name, patronymic, currency_date) " +
            "values (:chatId, :telephoneNum, :surname, :name, :patronymic, :currencyDate)")
    private String insertComeToDay;

    @Value("SELECT * FROM pass_schema.come_today")
    private String findAllComeToDay;

    @Autowired
    public ComeToDayRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void createComeToDay(ComeToDay comeToDay) throws DataAccessException {
        Map<String, Object> paramMap = getParamMap(comeToDay);
        jdbcTemplate.update(insertComeToDay, paramMap);
    }

    @Override
    public Optional<List<ComeToDay>> getComeToDay() throws DataAccessException {
        List<ComeToDay> allComeToDay = jdbcTemplate.query(findAllComeToDay, new ComeToDayRowMapper());
        return Optional.ofNullable(allComeToDay);
    }

    public static class ComeToDayRowMapper implements RowMapper<ComeToDay> {

        @Override
        public ComeToDay mapRow(ResultSet rs, int rowNum) throws SQLException {
            ComeToDay comeToDay = new ComeToDay();
            Long rsChatId = rs.getLong("chat_id");
            String rsPhoneNumber = rs.getString("tel_num");
            Date rsDateVisits = rs.getDate("currency_date");
            comeToDay.setChatId(rsChatId);
            comeToDay.setTelephoneNum(rsPhoneNumber);
            comeToDay.setCurrencyDate(rsDateVisits.toLocalDate());
            return comeToDay;
        }
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
