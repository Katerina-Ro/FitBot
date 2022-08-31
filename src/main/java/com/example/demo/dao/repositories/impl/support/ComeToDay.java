package com.example.demo.dao.repositories.impl.support;

import com.example.demo.dao.repositories.support.IComeToDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ComeToDay implements IComeToDay {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String GET_ALL_COME_TODAY = "SELECT * FROM pass_schema.come_today";

    private static final String DELETE_COME_TODAY = "DELETE from pass_schema.come_today " +
            "WHERE pass_schema.come_today.tel_num = :phoneNumber";

    @Autowired
    public ComeToDay(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<List<com.example.demo.dao.supportTables.ComeToDay>> getAllComeToDay() {
        List<com.example.demo.dao.supportTables.ComeToDay> comeToDayList = jdbcTemplate.query(GET_ALL_COME_TODAY,
                new ComeToDayRowMapper());
        return Optional.of(comeToDayList);
    }

    @Override
    public boolean deleteComeToDay(String phoneNumber) {
        int deletedPass = jdbcTemplate.update(DELETE_COME_TODAY, Map.of("phoneNumber", phoneNumber));
        return deletedPass > 0;
    }

    public static class ComeToDayRowMapper implements RowMapper<com.example.demo.dao.supportTables.ComeToDay> {
        @Override
        public com.example.demo.dao.supportTables.ComeToDay mapRow(ResultSet rs, int rowNum) throws SQLException {
            com.example.demo.dao.supportTables.ComeToDay comeToDay = new com.example.demo.dao.supportTables.ComeToDay();
            comeToDay.setSurname(rs.getString("surname"));
            comeToDay.setName(rs.getString("name"));
            comeToDay.setPatronymic(rs.getString("patronymic"));
            comeToDay.setTelephoneNum(rs.getString("tel_num"));
            comeToDay.setChatId(rs.getLong("chat_id"));
            Date rsDate = rs.getDate("currency_date");
            comeToDay.setCurrencyDate(rsDate.toLocalDate());
            return comeToDay;
        }
    }
}
