package telegramBot.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import telegramBot.model.Visitors;
import telegramBot.repositories.IVisitorsRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class VisitorsRepository implements IVisitorsRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public VisitorsRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Value("SELECT v FROM Visitors v WHERE v.telephoneNum = :telephoneNum")
    private String findVisitorByPhoneNumber;

    @Value("SELECT pass_schema.visitors.tel_num FROM pass_schema.visitors " +
            "WHERE pass_schema.visitors.chat_id = :chatId")
    private String findTelephoneNumByChatId;

    @Value("SELECT * FROM pass_schema.visitors WHERE pass_schema.visitors.chat_id = :chatId")
    private String findVisitorByChatId;

    @Value("SELECT pass_schema.visitors.chat_id FROM pass_schema.visitors " +
            "WHERE pass_schema.visitors.tel_num = :telephoneNum")
    private String findChatIdByPhoneNumber;

    @Value("")
    private String create;

    @Value("UPDATE pass_schema.visitors SET chat_id = :chatId WHERE tel_num = :phoneNumber")
    private String updateByPhoneNumber;

    @Value("")
    private String deleteVisitor;

    @Override
    public Optional<Visitors> findVisitorByPhoneNumber(String phoneNumber) {
        Visitors visitors = jdbcTemplate.query(findVisitorByPhoneNumber, Map.of("telephoneNum", phoneNumber),
                new VisitorsResultSetExtractor());
        return Optional.ofNullable(visitors);
    }

    @Override
    public Optional<Visitors> findVisitorByChatId(Long chatId) {
        Visitors visitors = jdbcTemplate.query(findVisitorByChatId, Map.of("chatId", chatId),
                new VisitorsResultSetExtractor());
        return Optional.ofNullable(visitors);
    }

    @Override
    public Optional<String> findTelephoneNumByChatId(Long chatId) {
        String phoneNumber = jdbcTemplate.query(findTelephoneNumByChatId, Map.of("chatId", chatId),
                new PhoneNumberResultSetExtractor());
        return Optional.ofNullable(phoneNumber);
    }

    @Override
    public Optional<Long> findChatIdByPhoneNumber(String phoneNumber) {
        Long chatId = jdbcTemplate.query(findChatIdByPhoneNumber, Map.of("telephoneNum", phoneNumber),
                new ChatIdResultSetExtractor());
        return Optional.ofNullable(chatId);
    }

    @Override
    public boolean create(Visitors visitor) {
        Map<String, Object> paramMap = new HashMap<>();
        Long chatId = visitor.getChatId();
        String phoneNumber = visitor.getTelephoneNum();
        String name = visitor.getName();
        String surName = visitor.getSurname();
        String patronymic = visitor.getPatronymic();
        paramMap.put("phoneNumber", phoneNumber);
        if (chatId != null) {
            paramMap.put("chatId", chatId);
        }
        if (name != null) {
            paramMap.put("name", name);
        }
        if (surName != null) {
            paramMap.put("surName", surName);
        }
        if (patronymic != null) {
            paramMap.put("patronymic", patronymic);
        }
        int updated = jdbcTemplate.update(create, paramMap);
        return updated > 0;
    }

    @Override
    public boolean updateByPhoneNumber(Visitors updateVisitors) {
        Map<String, Object> paramMap = new HashMap<>();
        Long chatId = updateVisitors.getChatId();
        String phoneNumber = updateVisitors.getTelephoneNum();
        String name = updateVisitors.getName();
        String surName = updateVisitors.getSurname();
        String patronymic = updateVisitors.getPatronymic();
        paramMap.put("phoneNumber", phoneNumber);
        if (chatId != null) {
            paramMap.put("chatId", chatId);
        }
        if (name != null) {
            paramMap.put("name", name);
        }
        if (surName != null) {
            paramMap.put("surName", surName);
        }
        if (patronymic != null) {
            paramMap.put("patronymic", patronymic);
        }
        int updatedVisitor = jdbcTemplate.update(updateByPhoneNumber, paramMap);
        return updatedVisitor > 0;
    }

    @Override
    public boolean deleteVisitor(String phoneNumber) {
        int deletedVisitor = jdbcTemplate.update(deleteVisitor, Map.of("phoneNumber", phoneNumber));
        return deletedVisitor > 0;
    }


    public static class VisitorsResultSetExtractor implements ResultSetExtractor<Visitors> {

        @Override
        public Visitors extractData(ResultSet rs) throws SQLException, DataAccessException {
            Visitors visitors = new Visitors();
            visitors.setTelephoneNum(rs.getString("tel_num"));
            visitors.setSurname(rs.getString("surname"));
            visitors.setName(rs.getString("name"));
            visitors.setPatronymic(rs.getString("patronumic"));
            return visitors;
        }
    }

    public static class PhoneNumberResultSetExtractor implements ResultSetExtractor<String>{
        @Override
        public String extractData(ResultSet rs) throws SQLException, DataAccessException {
            return rs.getString("tel_num");
        }
    }

    public static class ChatIdResultSetExtractor implements ResultSetExtractor<Long>{
        @Override
        public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
            return rs.getLong("chat_id");
        }
    }
}
