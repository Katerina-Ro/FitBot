package com.example.demo.dao.repositories.impl.support;

import com.example.demo.dao.repositories.support.IDontComeToDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class DontComeToDay implements IDontComeToDay {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String GET_ALL_DONT_COME_TODAY = "SELECT * FROM pass_schema.dont_come_today";

    @Autowired
    public DontComeToDay(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<List<com.example.demo.dao.supportTables.DontComeToDay>> getDontComeToDay() {
        List<com.example.demo.dao.supportTables.DontComeToDay> dontComeToDayList = jdbcTemplate.query(GET_ALL_DONT_COME_TODAY,
                new DontComeTodayRowMapper());
        return Optional.of(dontComeToDayList);
    }

    public static class DontComeTodayRowMapper implements RowMapper<com.example.demo.dao.supportTables.DontComeToDay> {
        @Override
        public com.example.demo.dao.supportTables.DontComeToDay mapRow(ResultSet rs, int rowNum) throws SQLException {
            com.example.demo.dao.supportTables.DontComeToDay dontComeToDay = new com.example.demo.dao.supportTables.DontComeToDay();
            dontComeToDay.setSurname(rs.getString("surname"));
            dontComeToDay.setName(rs.getString("name"));
            dontComeToDay.setPatronymic(rs.getString("patronymic"));
            dontComeToDay.setTelephoneNum(rs.getString("tel_num"));
            Date rsDate = rs.getDate("currency_date");
            dontComeToDay.setCurrencyDate(rsDate.toLocalDate());
            return dontComeToDay;
        }
    }
}
