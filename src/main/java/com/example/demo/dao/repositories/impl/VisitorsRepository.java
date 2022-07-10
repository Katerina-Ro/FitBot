package com.example.demo.dao.repositories.impl;

import com.example.demo.dao.Visitors;
import com.example.demo.dao.repositories.IVisitorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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

    private static final String FIND_VISITOR_BY_PHONE_NUMBER = "SELECT * FROM pass_schema.visitors " +
            "WHERE pass_schema.visitors.tel_num = :telephoneNum";

    private static final String FIND_PHONE_NUMBER_BY_CHAT_ID = "SELECT pass_schema.visitors.tel_num FROM pass_schema.visitors " +
            "WHERE pass_schema.visitors.chat_id = :chatId";

    private static final String FIND_VISITOR_BY_CHAT_ID = "SELECT * FROM pass_schema.visitors WHERE pass_schema.visitors.chat_id = :chatId";

    private static final String FIND_CHAT_ID_BY_PHONE_NUMBER = "SELECT pass_schema.visitors.chat_id FROM pass_schema.visitors " +
            "WHERE pass_schema.visitors.tel_num = :telephoneNum";

    private static final String CREATE_VISITOR = "INSERT into pass_schema.visitors (surname, name, patronumic, tel_num, chat_id) " +
            "values (:surName, :name, :patronymic, :phoneNumber, :chatId)";

    private static final String UPDATE_BY_PHONE_NUMBER = "UPDATE pass_schema.visitors SET surname = coalesce(:surName, surname), " +
            "name = coalesce(:name, name), patronumic = coalesce(:patronymic, patronumic), " +
            "tel_num = coalesce(:newPhoneNumber, tel_num) WHERE pass_schema.visitors.tel_num = :phoneNumber";

    private static final String DELETE_VISITOR = "DELETE from pass_schema.visitors WHERE tel_num = :phoneNumber";

    @Override
    public Optional<Visitors> findVisitorByPhoneNumber(String phoneNumber) {
        List<Visitors> visitor = jdbcTemplate.query(FIND_VISITOR_BY_PHONE_NUMBER, Map.of("telephoneNum", phoneNumber),
                new VisitorsRowMapper());
        if (visitor.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(visitor.get(0));
    }

    @Override
    public Optional<Visitors> findVisitorByChatId(Long chatId) {
        List<Visitors> visitor = jdbcTemplate.query(FIND_VISITOR_BY_CHAT_ID, Map.of("chatId", chatId),
                new VisitorsRowMapper());
        if (visitor.size() != 1) {
            throw new IllegalStateException(String.format("По chatId = %s в базе содержится 2 посетителя: %s ",
                    chatId, visitor));
        }
        return Optional.ofNullable(visitor.get(0));
    }

    @Override
    public Optional<String> findTelephoneNumByChatId(Long chatId) {
        List<String> phoneNumber = jdbcTemplate.query(FIND_PHONE_NUMBER_BY_CHAT_ID, Map.of("chatId", chatId),
                new PhoneNumberMapper());
        if (phoneNumber.size() != 1) {
            throw new IllegalStateException(String.format("По chatId = %s в базе содержится 2 номера телефона: %s ",
                    chatId, phoneNumber));
        }
        return Optional.ofNullable(phoneNumber.get(0));
    }

    @Override
    public Optional<Long> findChatIdByPhoneNumber(String phoneNumber) {
        List<Long> chatId = jdbcTemplate.query(FIND_CHAT_ID_BY_PHONE_NUMBER, Map.of("telephoneNum", phoneNumber),
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
        int updated = jdbcTemplate.update(CREATE_VISITOR, paramMap);
        return updated > 0;
    }

    @Override
    public boolean updateByPhoneNumber(Visitors updateVisitors) throws Exception {
        Map<String, Object> paramMap = getMapParams(updateVisitors);
        int updatedVisitor = jdbcTemplate.update(UPDATE_BY_PHONE_NUMBER, paramMap);
        return updatedVisitor > 0;
    }

    @Override
    public boolean deleteVisitor(String phoneNumber) {
        int deletedVisitor = jdbcTemplate.update(DELETE_VISITOR, Map.of("phoneNumber", phoneNumber));
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

    public static class ChatIdRowMapper implements RowMapper<Long>{
        @Override
        public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getLong("chat_id");
        }
    }

    private Map<String, Object> getMapParams(Visitors visitors) {
        Map<String, Object> paramMap = new HashMap<>();
        Long chatId = visitors.getChatId();
        String newPhoneNumber = visitors.getNewPhoneNumber();
        String phoneNumber = visitors.getTelephoneNum();
        String name = visitors.getName();
        String surName = visitors.getSurname();
        String patronymic = visitors.getPatronymic();
        paramMap.put("phoneNumber", phoneNumber);
        paramMap.put("newPhoneNumber", newPhoneNumber);
        paramMap.put("chatId", chatId);
        paramMap.put("name", name);
        paramMap.put("surName", surName);
        paramMap.put("patronymic", patronymic);
        return paramMap;
    }
}
