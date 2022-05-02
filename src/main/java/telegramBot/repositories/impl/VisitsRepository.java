package telegramBot.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import telegramBot.model.Visits;
import telegramBot.repositories.IVisitsRepository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class VisitsRepository implements IVisitsRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public VisitsRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Value("SELECT * From pass_schema.visits WHERE date_visit = :dateVisit")
    private String findAllPassIdByCurrencyDay;

    @Value("SELECT * From pass_schema.visits WHERE pass_id = :passId")
    private String findAllVisitsByPassId;

    @Value("update pass_schema.visits " +
            "set pass_schema.visits.date_visit = :dateVisit, " +
            "pass_schema.visits.count_visit = :countVisits " +
            "where pass_schema.visits.pass_id = :passId")
    private String updateVisit;

    @Override
    public Optional<List<Visits>> findAllPassBySpecifiedDay(Date specifiedDay) {
        List<Visits> visitsList = jdbcTemplate.query(findAllPassIdByCurrencyDay, Map.of("dateVisit", specifiedDay),
                new VisitsResultSetExtractor());
        return Optional.of(visitsList);
    }

    @Override
    public Optional<List<Visits>> findAllVisitsByPassId(Integer passId) {
        List<Visits> visits = jdbcTemplate.query(findAllVisitsByPassId, Map.of("passId", passId),
                new VisitsResultSetExtractor());
        return Optional.of(visits);
    }

    @Override
    public boolean createVisit(Visits visits) {
        return false;
    }

    @Override
    public boolean updateVisit(Visits visit) {
        Map<String, Object> paramsMap = getParamMap(visit);
        int updatedVisit = jdbcTemplate.update(updateVisit, paramsMap);
        return updatedVisit > 0;
    }

    @Override
    public boolean deleteVisit(Integer passId) {
        return false;
    }

    public static class VisitsResultSetExtractor implements RowMapper<Visits> {
        @Override
        public Visits mapRow(ResultSet rs, int rowNum) throws SQLException {
            Visits visit = new Visits();
            visit.setPass(rs.getInt("pass_id"));
            visit.setDateVisit(rs.getDate("date_visit").toLocalDate());
            visit.setCountVisit(rs.getInt("count_visit"));
            return visit;
        }
    }

    private Map<String, Object> getParamMap(Visits visit) {
        Map<String, Object> paramsMap = new HashMap<>();
        Integer passId = visit.getPass();
        Date dateVisit = null;
        if (visit.getDateVisit() != null) {
            dateVisit = Date.valueOf(visit.getDateVisit());
        }
        Integer countVisits = visit.getCountVisit();
        paramsMap.put("passId", passId);
        if (dateVisit != null) {
            paramsMap.put("dateVisit", dateVisit);
        }
        if (countVisits != null) {
            paramsMap.put("countVisits", countVisits);
        }
        return paramsMap;
    }
}
