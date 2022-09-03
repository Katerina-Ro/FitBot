package telegramBot.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import supportTable.DontComeToDay;
import telegramBot.repositories.IDontComeToDayRepository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class DontComeToDayRepository implements IDontComeToDayRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Value("insert into pass_schema.dont_come_today (chat_id, tel_num, surname, name, patronymic, currency_date)\n" +
            "values (:chatId, :telephoneNum, :surname, :name, :patronymic, :currencyDate)")
    private String insertDontComeToDay;

    @Value("SELECT * FROM pass_schema.dont_come_today")
    private String findAllDontComeToDay;

    @Autowired
    public DontComeToDayRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createDontComeToDay(DontComeToDay dontComeToDay) throws DataAccessException {
        Map<String, Object> paramMap = getParamMap(dontComeToDay);
        jdbcTemplate.update(insertDontComeToDay, paramMap);
    }

    @Override
    public Optional<List<DontComeToDay>> getDontComeToDay() throws DataAccessException {
        List<DontComeToDay> allComeToDay = jdbcTemplate.query(findAllDontComeToDay, new DontComeToDayMapper());
        return Optional.of(allComeToDay);
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

    public static class DontComeToDayMapper implements RowMapper<DontComeToDay> {

        @Override
        public DontComeToDay mapRow(ResultSet rs, int rowNum) throws SQLException {
            DontComeToDay dontComeToDay = new DontComeToDay();
            Long rsChatId = rs.getLong("chat_id");
            String rsPhoneNumber = rs.getString("tel_num");
            Date rsDateVisits = rs.getDate("currency_date");
            dontComeToDay.setChatId(rsChatId);
            dontComeToDay.setTelephoneNum(rsPhoneNumber);
            dontComeToDay.setCurrencyDate(rsDateVisits.toLocalDate());
            return dontComeToDay;
        }
    }
}
