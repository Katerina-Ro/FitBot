package com.example.demo.modelService;

import com.example.demo.dao.Visits;
import com.example.demo.dao.repositories.IVisitsRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VisitsService {
    @Getter
    private final IVisitsRepository visitsRepository;

    private static final String message = "Нет занятий в абонементе";
    private static final String messagePassException = "Абонемент не занесен в базу или произошел разрыв соединения " +
            "с базой данных. Попробуйте еще раз";

    @Autowired
    public VisitsService(IVisitsRepository visitsRepository) {
        this.visitsRepository = visitsRepository;
    }

    /**
     * Внесение информации о визитах к конкретному абонементу студента
     * @param passId идентификатор абонемента
     * @param dateVisit - дата посещения
     * @return - true, если занесении информации о конкретном посещении прошло успешно
     */
    public boolean updateVisit(Integer passId, LocalDate dateVisit, Integer countVisit) {
        Optional<List<Visits>> visitList = getVisitFromDB(passId);
        if (visitList.isPresent()) {
            for (Visits v: visitList.get()) {
                if (dateVisit.equals(v.getDateVisit())) {
                    v.setCountVisit(countVisit);
                    return visitsRepository.updateVisit(v);
                }
            }
        }
        return false;
    }

    public boolean createVisit(Visits visit) {
        return visitsRepository.createVisit(visit);
    }

    /**
     * Получение информации о визитах
     * @param passId - номер абонемента из БД
     */
    public Optional<List<Visits>> getVisitFromDB(Integer passId) {
        return visitsRepository.findAllVisitsByPassId(passId);
    }

    public boolean minusVisit(Integer passId, int inputNumber, LocalDate specifiedDay) {
        Optional<List<Visits>> list = getVisitFromDB(passId);
        if(list.isPresent()) {
            for (Visits v: list.get()) {
                if (specifiedDay.equals(v.getDateVisit())) {
                    Integer countVisitFromDB = v.getCountVisit();
                    if (countVisitFromDB >= inputNumber) {
                        v.setCountVisit(v.getCountVisit() - inputNumber);
                        return visitsRepository.updateVisit(v);
                    }
                }
            }
        }
        return false;
    }

    private Integer calculateCountVisit(Integer countVisit) {
        return ++countVisit;
    }

    /**
     * Получить номера абонементов студентов, которые придут в указанный день
     * @param specifiedDate - указанный день
     * @return список номеров абонементов
     */
    public Optional<List<Integer>> getPassIdBySpecifiedDay(Date specifiedDate) {
        Optional<List<Visits>> visitsList = visitsRepository.findAllPassBySpecifiedDay(specifiedDate);
        List<Integer> listPassId = new ArrayList<>();
        if (visitsList.isPresent()) {
            for (Visits v: visitsList.get()) {
                listPassId.add(v.getPass());
            }
        }
        return Optional.ofNullable(listPassId);
    }


}
