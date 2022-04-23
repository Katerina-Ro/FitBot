package telegramBot.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import telegramBot.model.Pass;
import telegramBot.repositories.IPassRepository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class PassRepository implements IPassRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Value("SELECT pass_schema.pass_table.phoneNumber FROM pass_schema.pass_table " +
            "WHERE pass_schema.pass_table.pass_id = :numPass")
    private String findPhoneNumberByPassId;

    @Value("SELECT * FROM pass_schema.pass_table WHERE pass_schema.pass_table.tel_num = :phoneNumber")
    private String findPassByPhone;

    @Value("")
    private String findPassByPassId;

    @Value("")
    private String createPass;

    @Value("")
    private String updatePass;

    @Value("")
    private String deletePass;

    @Autowired
    public PassRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<String> findPhoneNumberByPassId(Integer passId) {
        String phoneNumber = jdbcTemplate.query(findPhoneNumberByPassId, Map.of("numPass", passId),
                new PhoneNumberResultSetExtractor());
        return Optional.ofNullable(phoneNumber);
    }

    @Override
    public Optional<List<Pass>> findPassByPhone(String phoneNumber) {
        List<Pass> passList = jdbcTemplate.query(findPassByPhone, Map.of("phoneNumber", phoneNumber),
                new PassRowMapper());
        return Optional.of(passList);
    }

    @Override
    public Optional<Pass> findPassByPassId(Integer passId) {
        Pass pass = jdbcTemplate.query(findPassByPassId, Map.of("passId",passId),
                new PassResultSetExtractor());
        return Optional.ofNullable(pass);
    }

    @Override
    public boolean createPass(Pass pass) {
        Map<String, Object> paramMap = new HashMap<>();
        String phoneNumber = pass.getPhoneNumber();
        Date dateStart = Date.valueOf(pass.getDateStart());
        Date dateEnd = Date.valueOf(pass.getDateEnd());
        Integer visitLimit = pass.getVisitLimit();
        Integer freezeLimit = pass.getFreezeLimit();
        Date dateStartFreeze = null;
        if (pass.getDateStartFreeze() != null) {
            dateStartFreeze = Date.valueOf(pass.getDateStartFreeze());
        }
        paramMap.put("phoneNumber", phoneNumber);
        paramMap.put("dateStart", dateStart);
        paramMap.put("dateEnd", dateEnd);
        paramMap.put("visitLimit", visitLimit);
        if (freezeLimit != null) {
            paramMap.put("freezeLimit", freezeLimit);
        }
        if (dateStartFreeze != null) {
            paramMap.put("dateStartFreeze",dateStartFreeze);
        }
        int createdPass = jdbcTemplate.update(createPass, paramMap);
        return createdPass > 0;
    }

    @Override
    public boolean update(Pass updatedPass) {
        Map<String, Object> paramMap = new HashMap<>();
        String phoneNumber = updatedPass.getPhoneNumber();
        Date dateStart = Date.valueOf(updatedPass.getDateStart());
        Date dateEnd = Date.valueOf(updatedPass.getDateEnd());
        Integer visitLimit = updatedPass.getVisitLimit();
        Integer freezeLimit = updatedPass.getFreezeLimit();
        Date dateStartFreeze = null;
        if (updatedPass.getDateStartFreeze() != null) {
            dateStartFreeze = Date.valueOf(updatedPass.getDateStartFreeze());
        }
        paramMap.put("phoneNumber", phoneNumber);
        paramMap.put("dateStart", dateStart);
        paramMap.put("dateEnd", dateEnd);
        paramMap.put("visitLimit", visitLimit);
        if (freezeLimit != null) {
            paramMap.put("freezeLimit", freezeLimit);
        }
        if (dateStartFreeze != null) {
            paramMap.put("dateStartFreeze",dateStartFreeze);
        }
        int updatePass = jdbcTemplate.update(this.updatePass, paramMap);
        return updatePass > 0;
    }

    @Override
    public boolean deletePass(Integer passId) {
        int deletedPass = jdbcTemplate.update(deletePass, Map.of("passId", passId));
        return deletedPass > 0;
    }

    public static class PhoneNumberResultSetExtractor implements ResultSetExtractor<String>{
        @Override
        public String extractData(ResultSet rs) throws SQLException, DataAccessException {
            return rs.getString("tel_num");
        }
    }

    public static class PassResultSetExtractor implements ResultSetExtractor<Pass> {

        @Override
        public Pass extractData(ResultSet rs) throws SQLException, DataAccessException {
            Pass pass = new Pass();
            pass.setNumPass(rs.getInt("pass_id"));
            pass.setPhoneNumber(rs.getString("tel_num"));
            pass.setDateStart(rs.getDate("date_start").toLocalDate());
            pass.setDateEnd(rs.getDate("date_end").toLocalDate());
            pass.setVisitLimit(rs.getInt("visit_limit"));
            pass.setFreezeLimit(rs.getInt("freeze_limit"));
            pass.setDateStartFreeze(rs.getDate("date_freeze").toLocalDate());
            return pass;
        }
    }

        public static class PassRowMapper implements RowMapper<Pass> {

        @Override
        public Pass mapRow(ResultSet rs, int rowNum) throws SQLException {
            Pass pass = new Pass();
            pass.setNumPass(rs.getInt("pass_id"));
            pass.setPhoneNumber(rs.getString("tel_num"));
            pass.setDateStart(rs.getDate("date_start").toLocalDate());
            pass.setDateEnd(rs.getDate("date_end").toLocalDate());
            pass.setVisitLimit(rs.getInt("visit_limit"));
            pass.setFreezeLimit(rs.getInt("freeze_limit"));
            pass.setDateStartFreeze(rs.getDate("date_freeze").toLocalDate());
            return pass;
        }
    }
}
