package telegramBot.service.entetiesService;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import telegramBot.enteties.Pass;
import telegramBot.enteties.Visitors;
import telegramBot.enteties.Visits;
import telegramBot.repositories.PassRepository;
import telegramBot.repositories.VisitorsRepository;
import telegramBot.repositories.VisitsRepository;

import javax.annotation.Nullable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VisitorsService {
    @Getter
    private final VisitorsRepository visitorsRepository;
    private final PassRepository passRepository;
    private final VisitsRepository visitsRepository;

    @Autowired
    public VisitorsService(VisitorsRepository visitorsRepository, PassRepository passRepository, VisitsRepository visitsRepository) {
        this.visitorsRepository = visitorsRepository;
        this.passRepository = passRepository;
        this.visitsRepository = visitsRepository;
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
     * Получение информации об абонементе
     * @param chatId - уникальный номер пользователя в telegram bot
     */
    public Optional<List<Pass>> getPassByChatId(Long chatId) {
        Optional<Visitors> visitor = getVisitor(chatId);
        return visitor.map(Visitors::getPassList);
    }

    /**
     * Получение информации об абонементе
     * @param phoneNumber - номер телефона студента
     */
    public Optional<List<Pass>> getPassByChatId(String phoneNumber) {
        Optional<Visitors> visitors = getVisitor(phoneNumber);
        return visitors.map(Visitors::getPassList);
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

    /**
     * Получение информации о визитах
     * @param chatId - идентификатор студента в Телеграмме
     */
    public Optional<List<Visits>> getVisit(Long chatId, Integer passId) {
        Optional<List<Pass>> pass = getPassByChatId(chatId);
        return pass.map(passes -> passes.get(passId).getVisits());
    }

    /**
     * Получить список всех студентов, кто придет в текущий день
     */
    public List<Visitors> getVisitorsListToDay() {
        List<Visitors> listVisitorsToDay = new ArrayList<>();
        LocalDate currencyDay = LocalDate.now(ZoneId.of("GMT+03:00"));
        Date sqlDate = Date.valueOf(currencyDay);
        Optional<List<Integer>> listPassId =  visitsRepository.findAllPassIdByCurrencyDay(sqlDate);
        if (listPassId.isPresent()) {
            for(Integer i: listPassId.get()) {
                Long chatId = passRepository.findChatIdByPassId(i);
                Optional<Visitors> visitors = getVisitor(chatId);
                visitors.ifPresent(listVisitorsToDay::add);
            }
        }
        return listVisitorsToDay;
    }
}
