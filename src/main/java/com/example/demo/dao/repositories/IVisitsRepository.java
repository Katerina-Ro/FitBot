package com.example.demo.dao.repositories;

import com.example.demo.dao.Visits;
import com.example.demo.exception.ExceptionDB;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * {@link Repository для работы с сущностью {@link Visits}
 */
public interface IVisitsRepository {

    Optional<List<Visits>> findAllPassBySpecifiedDay(Date specifiedDay);

    Optional<List<Visits>> findAllVisitsByPassId(Integer passId);

    boolean createVisit(Visits visits) throws ExceptionDB;

    boolean updateVisit(Visits visit);

    boolean deleteVisit(Integer passId);

    boolean deleteVisit(LocalDate date);
}
