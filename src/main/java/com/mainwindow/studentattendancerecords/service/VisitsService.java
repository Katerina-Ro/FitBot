package com.mainwindow.studentattendancerecords.service;

import db.enteties.Pass;
import db.enteties.Visits;
import db.repositories.VisitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VisitsService {
    private final VisitsRepository visitsRepository;
    private final PassService passService;

    private static final String message = "Нет занятий в абонементе";
    private static final String messagePassException = "Абонемент не занесен в базу или произошел разрыв соединения " +
            "с базой данных. Попробуйте еще раз";

    @Autowired
    public VisitsService(VisitsRepository visitsRepository, PassService passService) {
        this.visitsRepository = visitsRepository;
        this.passService = passService;
    }

    /**
     * Внесение информации о визитах к конкретному абонементу студента
     * @param passId идентификатор абонемента
     * @param dateVisit - дата посещения
     * @return - true, если занесении информации о конкретном посещении прошло успешно
     */
    public boolean createVisit(Integer passId, LocalDate dateVisit) {
        Optional<Visits> visit = getVisit(passId);
        if (visit.isPresent()) {
            visit.get().setDateVisit(dateVisit);
            visit.get().setCountVisit(calculateCountVisit(visit.get().getCountVisit()));
            visitsRepository.save(visit.get());
            return true;
        }
        return false;
    }

    /**
     * Получение информации о визитах
     * @param chatId - идентификатор студента в Телеграмме
     */
    public Optional<List<Visits>> getVisit(Long chatId, Integer passId) {
        Optional<List<Pass>> pass = passService.getPassByChatId(chatId);
        return pass.map(passes -> passes.get(passId).getVisits());
    }

    /**
     * Получение информации о визитах
     * @param passId - номер абонемента из БД
     */
    public Optional<Visits> getVisit(Integer passId) {
        return visitsRepository.findById(passId);
    }

    public boolean minusVisit(Integer passId, int inputNumber) {
        if(getVisit(passId).isPresent()) {
            Visits visits = getVisit(passId).get();
            visits.setCountVisit(inputNumber);
            return true;
        }
        return false;
    }

    /**
     * Получить номера абонементов студентов, которые придут в указанный день
     * @param currencyDay - текущий день
     * @return список номеров абонементов
     */
    public Optional<List<Integer>> getListPassId(LocalDate currencyDay) {
        return visitsRepository.findAllPassIdByCurrencyDay(currencyDay);
    }

    private Integer calculateCountVisit(Integer countVisit) {
        return ++countVisit;
    }
}
