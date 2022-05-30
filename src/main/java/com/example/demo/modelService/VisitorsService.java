package com.example.demo.modelService;

import com.example.demo.dao.Visitors;
import com.example.demo.dao.repositories.IPassRepository;
import com.example.demo.dao.repositories.IVisitorsRepository;
import com.example.demo.exception.SeveralException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VisitorsService {
    @Getter
    private final IVisitorsRepository visitorsRepository;
    private final IPassRepository passRepository;
    //private final IVisitsRepository visitsRepository;
    private final VisitsService visitsService;

    @Autowired
    public VisitorsService(IVisitorsRepository visitorsRepository, IPassRepository passRepository,
                          VisitsService visitsService) {
        this.visitorsRepository = visitorsRepository;
        this.passRepository = passRepository;
        this.visitsService = visitsService;
    }

    /**
     * Проверка, есть ли у этого chatId номер телефона в базе
     */
    public boolean havPhoneNumber(Long chatId) {
        Optional<String> phoneNumberInDB = visitorsRepository.findTelephoneNumByChatId(chatId);
        return phoneNumberInDB.isPresent();
    }

    public boolean havChatId(String phoneNumber) {
        Optional<Long> chatIDinDB = visitorsRepository.findChatIdByPhoneNumber(phoneNumber);
        return chatIDinDB.isPresent();
    }

    /**
     * Проверка, есть ли номер телефона в базе
     */
    public boolean existPhoneNumber(String phoneNumber) throws SeveralException {
        Optional<Visitors> visitor = visitorsRepository.findVisitorByPhoneNumber(phoneNumber);
        return visitor.isPresent();
    }

    /**
     * Получение информации об абонементе
     * @param chatId - уникальный номер пользователя в telegram bot

    public Optional<List<Pass>> getPassByChatId(Long chatId) {
        Optional<Visitors> visitor = getVisitorByPhone(chatId);
        return visitor.map(Visitors::getPassList);
    }*/

    /**
     * Получение информации об абонементе
     * @param phoneNumber - номер телефона студента

    public Optional<List<Pass>> getPassByPhoneNumber(String phoneNumber) {
        Optional<Visitors> visitors = getVisitorByPhone(phoneNumber);
        return visitors.map(Visitors::getPassList);
    }  */

    /**
     * Заносим номер телефона в базу данных по chatId
     */
    public void createVisitorsBot(Long chatId, String phoneNumber) {
        System.out.println("вписываем телефон в базу данных");
        Visitors visitor = new Visitors();
        visitor.setChatId(chatId);
        visitor.setTelephoneNum(phoneNumber);
        visitorsRepository.create(visitor);
    }

    /**
     * Занесение данных о студенте (только для админов)

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
        visitorsRepository.create(visitor);
    }  */

    /**
     * Получить информацию о студенте по chatId
     * @param chatId - идентификатор студента в Телеграмме
     */
    public Optional<Visitors> getVisitorByChatId(Long chatId) {
        return visitorsRepository.findVisitorByChatId(chatId);
    }

    /**
     * Получить данные студента по номеру телефона
     * @param phoneNumber - номер телефона студента
     */
    public Optional<Visitors> getVisitorByPhone(String phoneNumber) throws SeveralException {
        return visitorsRepository.findVisitorByPhoneNumber(phoneNumber.trim());
    }

    /**
     * Удаление информации о студенте из БД (только для админов)
     * @param chatId - идентификатор студента в Телеграмм
     * @return true, если удаление прошло успешно
     */
    public boolean deleteVisitors(Long chatId) {
        BigDecimal bigDecimal = BigDecimal.valueOf(chatId);
        Integer chatIdForDB = Integer.valueOf(String.valueOf(bigDecimal));
        //visitorsRepository.deleteById(chatIdForDB);
        return true;
    }

    /**
     * Удаление информации о студенте (доступно только админам)
     * @param phoneNumber - номер телефона студента
     * @return true, если удаление прошло успешно

    public boolean deleteVisitors(String phoneNumber) {
        Optional<Visitors> visitor = getVisitor(phoneNumber);
        if (visitor.isPresent()) {
            Long chatId = visitor.get().getChatId();
            visitorsRepository.deleteById(chatId);
            return true;
        }
        return false;
    } */

    /**
     * Обновление информации о студенте
     * @param phoneNumber
     * @param surname - фамилия
     * @param patronymic - отчество
     * @return true, если обновление прошло успешно
     */
    public boolean updateInfoOfVisitor(String phoneNumber, String name, String surname,
                                       String patronymic, String newPhoneNumber) throws SeveralException {
        Optional<Visitors> visitors = getVisitorByPhone(phoneNumber);
        if (visitors.isPresent()) {
            if (name != null) {
                visitors.get().setName(name);
            }
            if (surname != null) {
                visitors.get().setSurname(surname);
            }
            if (patronymic != null) {
                visitors.get().setPatronymic(patronymic);
            }
            if (newPhoneNumber != null) {
                visitors.get().setTelephoneNum(newPhoneNumber);
            }
            visitorsRepository.updateByPhoneNumber(visitors.get());
            return true;
        }
        return false;
    }

    /**
     * Изменение номера телефона студента
     * @param phoneNumber - имеющийся в базе номер телефона студента
     * @param newPhoneNumber - новый номер телефона
     * @return true, если номер телефона изменен

    public boolean changePhoneNumberOfVisitor(String phoneNumber, String newPhoneNumber) {
        Optional<Visitors> visitors = getVisitorByPhone(phoneNumber);
        if (visitors.isPresent()) {
            visitors.get().setTelephoneNum(newPhoneNumber);
            visitorsRepository.save(visitors.get());
            return true;
        }
        return false;
    } */

    /**
     * Получение информации о визитах
     * @param chatId - идентификатор студента в Телеграмме

    public Optional<List<Visits>> getVisit(Long chatId, Integer passId) {
        Optional<List<Pass>> pass = getPassByChatId(chatId);
        return pass.map(passes -> passes.get(passId).getVisits());
    }*/

    /**
     * Получить список всех студентов, кто придет в текущий день
     */
    public List<Visitors> getVisitorsListToDay() throws SeveralException {
        List<Visitors> listVisitorsToDay = new ArrayList<>();
        LocalDate currencyDay = LocalDate.now(ZoneId.of("GMT+03:00"));
        Date sqlDate = Date.valueOf(currencyDay);
        Optional<List<Integer>> listPassId = visitsService.getPassIdBySpecifiedDay(sqlDate);
        if (listPassId.isPresent()) {
            for(Integer i: listPassId.get()) {
                Optional<String> phoneNumber = passRepository.findPhoneNumberByPassId(i);
                if (phoneNumber.isPresent()) {
                    Optional<Visitors> visitors = getVisitorByPhone(phoneNumber.get());
                    visitors.ifPresent(listVisitorsToDay::add);
                }
            }
        }
        return listVisitorsToDay;
    }
}
