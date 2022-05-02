package telegramBot.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import telegramBot.model.Visitors;
import telegramBot.repositories.IVisitorsRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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

    @Value("insert into pass_schema.visitors (surname, name, patronumic, tel_num, chat_id) " +
            "values (:surName, :name, :patronymic, :phoneNumber, :chatId)")
    private String create;

    @Value("UPDATE pass_schema.visitors SET chat_id = :chatId " +
            "WHERE pass_schema.visitors.tel_num = :phoneNumber")
    private String updateByPhoneNumber;

    @Value("delete from pass_schema.visitors where tel_num = :phoneNumber")
    private String deleteVisitor;

    @Override
    public Optional<Visitors> findVisitorByPhoneNumber(String phoneNumber) {
        List<Visitors> visitor = jdbcTemplate.query(findVisitorByPhoneNumber, Map.of("telephoneNum", phoneNumber),
                new VisitorsRowMapper());
        if (visitor.size() != 1) {
            throw new IllegalStateException(String.format("По phoneNumber = %s в базе содержится 2 chatId: %s ",
                    phoneNumber, visitor));
        }
        return Optional.ofNullable(visitor.get(0));
    }

    @Override
    public Optional<Visitors> findVisitorByChatId(Long chatId) {
        List<Visitors> visitor = jdbcTemplate.query(findVisitorByChatId, Map.of("chatId", chatId),
                new VisitorsRowMapper());
        if (visitor.size() != 1) {
            throw new IllegalStateException(String.format("По chatId = %s в базе содержится 2 посетителя: %s ",
                    chatId, visitor));
        }
        return Optional.ofNullable(visitor.get(0));
    }

    @Override
    public Optional<String> findTelephoneNumByChatId(Long chatId) {
        List<String> phoneNumber = jdbcTemplate.query(findTelephoneNumByChatId, Map.of("chatId", chatId),
                new PhoneNumberMapper());
        if (phoneNumber.size() != 1) {
            throw new IllegalStateException(String.format("По chatId = %s в базе содержится 2 номера телефона: %s ",
                    chatId, phoneNumber));
        }
        return Optional.ofNullable(phoneNumber.get(0));
    }

    @Override
    public Optional<Long> findChatIdByPhoneNumber(String phoneNumber) {
        List<Long> chatId = jdbcTemplate.query(findChatIdByPhoneNumber, Map.of("telephoneNum", phoneNumber),
                new ChatIdRowMapper());
        if (chatId.size() != 1) {
            throw new IllegalStateException(String.format("По phoneNumber = %s в базе содержится 2 chatId: %s ",
                    phoneNumber, chatId));
        }
        return Optional.ofNullable(chatId.get(0));
    }

    @Override
    public boolean create(Visitors visitor) {
        Map<String, Object> paramMap = getMapParams(visitor);
        int updated = jdbcTemplate.update(create, paramMap);
        return updated > 0;
    }

    @Override
    public boolean updateByPhoneNumber(Visitors updateVisitors) {
        Map<String, Object> paramMap = getMapParams(updateVisitors);
        int updatedVisitor = jdbcTemplate.update(updateByPhoneNumber, paramMap);
        return updatedVisitor > 0;
    }

    @Override
    public boolean deleteVisitor(String phoneNumber) {
        int deletedVisitor = jdbcTemplate.update(deleteVisitor, Map.of("phoneNumber", phoneNumber));
        return deletedVisitor > 0;
    }

    public static class VisitorsRowMapper implements RowMapper<Visitors> {

        @Override
        public Visitors mapRow(ResultSet rs, int rowNum) throws SQLException {
            Visitors visitors = new Visitors();
            visitors.setTelephoneNum(rs.getString("tel_num"));
            visitors.setSurname(rs.getString("surname"));
            visitors.setName(rs.getString("name"));
            visitors.setPatronymic(rs.getString("patronumic"));
            return visitors;
        }
    }

    public static class PhoneNumberMapper implements RowMapper<String> {
        @Override
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString("tel_num");
        }
    }
    /*
    public static class PhoneNumberResultSetExtractor implements ResultSetExtractor<String>{
        @Override
        public String extractData(ResultSet rs) throws SQLException, DataAccessException {
            String g = rs.getString("tel_num");
            if (rs.next()) {
                return g;
            }
            return StringUtils.EMPTY;
        }
    } */

    public static class ChatIdRowMapper implements RowMapper<Long>{

        @Override
        public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getLong("chat_id");
        }
    }

    private Map<String, Object> getMapParams(Visitors visitors) {
        Map<String, Object> paramMap = new HashMap<>();
        Long chatId = visitors.getChatId();
        String phoneNumber = visitors.getTelephoneNum();
        String name = visitors.getName();
        String surName = visitors.getSurname();
        String patronymic = visitors.getPatronymic();
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
        return paramMap;
    }
}
