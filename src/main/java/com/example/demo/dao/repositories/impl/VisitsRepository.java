package com.example.demo.dao.repositories.impl;

import com.example.demo.dao.Visits;
import com.example.demo.dao.repositories.IVisitsRepository;
import com.example.demo.exception.ExceptionDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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

    private static final String FIND_ALL_PASS_ID_BY_CURRENCY_DAY = "SELECT * From pass_schema.visits WHERE date_visit = :dateVisit";

    private static final String FIND_ALL_VISITS_BY_PASS_ID = "SELECT * From pass_schema.visits WHERE pass_id = :passId";

    private static final String INSERT_VISIT = "";

    private static final String UPDATE_VISIT = "UPDATE pass_schema.visits " +
            "SET pass_schema.visits.date_visit = :dateVisit, " +
            "pass_schema.visits.count_visit = :countVisits " +
            "WHERE pass_schema.visits.pass_id = :passId";

    private static final String DELETE_VISITS = "DELETE from pass_schema.visits " +
            "WHERE pass_schema.visits.pass_id = :passId";

    @Override
    public Optional<List<Visits>> findAllPassBySpecifiedDay(Date specifiedDay) {
        List<Visits> visitsList = jdbcTemplate.query(FIND_ALL_PASS_ID_BY_CURRENCY_DAY, Map.of("dateVisit", specifiedDay),
                new VisitsResultSetExtractor());
        return Optional.of(visitsList);
    }

    @Override
    public Optional<List<Visits>> findAllVisitsByPassId(Integer passId) {
        List<Visits> visits = jdbcTemplate.query(FIND_ALL_VISITS_BY_PASS_ID, Map.of("passId", passId),
                new VisitsResultSetExtractor());
        return Optional.of(visits);
    }

    @Override
    public boolean createVisit(Visits visits) throws ExceptionDB {
        Map<String, Object> paramMap = getParamMap(visits);
        int createdPass = jdbcTemplate.update(INSERT_VISIT, paramMap);
        return createdPass > 0;
    }

    @Override
    public boolean updateVisit(Visits visit) {
        Map<String, Object> paramsMap = getParamMap(visit);
        int updatedVisit = jdbcTemplate.update(UPDATE_VISIT, paramsMap);
        return updatedVisit > 0;
    }

    @Override
    public boolean deleteVisit(Integer passId) {
        int deletedVisits = jdbcTemplate.update(DELETE_VISITS, Map.of("passId", passId));
        return deletedVisits > 0;
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
