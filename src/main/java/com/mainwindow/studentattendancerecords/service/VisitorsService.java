package com.mainwindow.studentattendancerecords.service;

import db.enteties.Pass;
import db.enteties.Visitors;
import db.repositories.VisitorsRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VisitorsService {
    private final VisitorsRepository visitorsRepository;
    private final PassService passService;
    private final VisitsService visitsService;

    @Autowired
    public VisitorsService(VisitorsRepository visitorsRepository, PassService passService, VisitsService visitsService) {
        this.visitorsRepository = visitorsRepository;
        this.passService = passService;
        this.visitsService = visitsService;
    }

    /**
     * Проверка, есть ли у этого chatId номер телефона в базе
     */
    public boolean havPhoneNumber(Long chatId) {
        Optional<Visitors> visitors = visitorsRepository.findById(chatId);
        return visitors.filter(value -> value.getTelephoneNum() != null).isPresent();
    }

    /**
     * Проверка, есть ли номер телефона в базе
     */
    public boolean existPhoneNumber(String phoneNumber) {
        return visitorsRepository.findTelephoneNum(phoneNumber) != null;
    }

    /**
     * Заносим номер телефона в базу данных по chatId
     */
    public void createVisitorsBot(Long chatId, String phoneNumber, String name, @Nullable String surname) {
        Visitors visitor = new Visitors();
        visitor.setChatId(chatId);
        visitor.setTelephoneNum(phoneNumber);
        visitor.setName(name);
        if (surname != null) {
            visitor.setSurname(surname);
        }
        visitorsRepository.save(visitor);
    }

    /**
     * Занесение данных о студенте (только для админов)
     */
    public void createVisitorsAdmin(String phoneNumber, String name, @Nullable String surname,
                                    @Nullable String patronymic, @Nullable Pass pass) {
        Visitors visitor = new Visitors();
        visitor.setTelephoneNum(phoneNumber);
        visitor.setName(name);
        if(surname != null) {
            visitor.setSurname(surname);
        }
        if(patronymic != null) {
            visitor.setPatronymic(patronymic);
        }
        if(pass != null) {
            List<Pass> listPass = visitor.getPassList();
            listPass.add(pass);
            visitor.setPassList(listPass);
        }
        visitorsRepository.save(visitor);
    }

    /**
     * Получить список всех студентов, кто придет в текущий день
     */
    public List<Visitors> getVisitorsListToDay() {
        List<Visitors> listVisitorsToDay = new ArrayList<>();
        LocalDate currencyDay = LocalDate.now(ZoneId.of("GMT+03:00"));
        Optional<List<Integer>> listPassId = visitsService.getListPassId(currencyDay);
        if (listPassId.isPresent()) {
            for(Integer i: listPassId.get()) {
                Long chatId = passService.getChatId(i);
                Optional<Visitors> visitors = getVisitor(chatId);
                visitors.ifPresent(listVisitorsToDay::add);
            }
        }
        return listVisitorsToDay;
    }

    /**
     * Получить информацию о студенте по chatId
     * @param chatId - идентификатор студента в Телеграмме
     */
    public Optional<Visitors> getVisitor(Long chatId) {
        return visitorsRepository.findById(chatId);
    }

    /**
     * Получить данные студента по номеру телефона
     * @param phoneNumber - номер телефона студента
     */
    public Optional<Visitors> getVisitor(String phoneNumber) {
        return visitorsRepository.findVisitorByPhoneNumber(phoneNumber.trim());
    }

    /**
     * Получить данные студента по номеру абонемента
     * @param passId - номер абонемента
     */
    public Optional<Visitors> getVisitor(Integer passId) {
        return getVisitor(passService.getChatId(passId));
    }

    /**
     * Удаление информации о студенте из БД (только для админов)
     * @param chatId - идентификатор студента в Телеграмм
     * @return true, если удаление прошло успешно
     */
    public boolean deleteVisitors(Long chatId) {
        visitorsRepository.deleteById(chatId);
        return true;
    }

    /**
     * Удаление информации о студенте (доступно только админам)
     * @param phoneNumber - номер телефона студента
     * @return true, если удаление прошло успешно
     */
    public boolean deleteVisitors(String phoneNumber) {
        Optional<Visitors> visitor = getVisitor(phoneNumber);
        if (visitor.isPresent()) {
            Long chatId = visitor.get().getChatId();
            visitorsRepository.deleteById(chatId);
            return true;
        }
        return false;
    }

    /**
     * Обновление информации о студенте
     * @param phoneNumber
     * @param surname - фамилия
     * @param patronymic - отчество
     * @return true, если обновление прошло успешно
     */
    public boolean updateInfoOfVisitor(String phoneNumber, String surname, String patronymic) {
        Optional<Visitors> visitors = getVisitor(phoneNumber);
        if (visitors.isPresent()) {
            visitors.get().setSurname(surname);
            visitors.get().setPatronymic(patronymic);
            visitorsRepository.save(visitors.get());
            return true;
        }
        return false;
    }

    /**
     * Изменение номера телефона студента
     * @param phoneNumber - имеющийся в базе номер телефона студента
     * @param newPhoneNumber - новый номер телефона
     * @return true, если номер телефона изменен
     */
    public boolean changePhoneNumberOfVisitor(String phoneNumber, String newPhoneNumber) {
        Optional<Visitors> visitors = getVisitor(phoneNumber);
        if (visitors.isPresent()) {
            visitors.get().setTelephoneNum(newPhoneNumber);
            visitorsRepository.save(visitors.get());
            return true;
        }
        return false;
    }
}
