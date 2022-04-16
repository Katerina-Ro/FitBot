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
    public boolean havPhoneNumber(String chatId) {
        Optional<String> visitors = visitorsRepository.findVisitorByChatId(chatId);
        boolean b = false;
        if (visitors.isPresent()) {
            b = true;
        }
        return b;
    }

    public boolean havChatId(String phoneNumber) {
        Optional<Visitors> visitors = visitorsRepository.findChatIdByPhoneNumber(phoneNumber);
        String chatId = null;
        if (visitors.isPresent()) {
            chatId = visitors.get().getChatId();
        }
        return chatId != null;
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
    public Optional<List<Pass>> getPassByChatId(String chatId) {
        Optional<Visitors> visitor = getVisitorByPhone(chatId);
        return visitor.map(Visitors::getPassList);
    }

    /**
     * Получение информации об абонементе
     * @param phoneNumber - номер телефона студента
     */
    public Optional<List<Pass>> getPassByPhoneNumber(String phoneNumber) {
        Optional<Visitors> visitors = getVisitorByPhone(phoneNumber);
        return visitors.map(Visitors::getPassList);
    }

    /**
     * Заносим номер телефона в базу данных по chatId
     */
    public void createVisitorsBot(String chatId, String phoneNumber) {
        System.out.println("вписываем телефон в базу данных");
        Visitors visitor = new Visitors();
        visitor.setChatId(chatId);
        visitor.setTelephoneNum(phoneNumber);
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
    public Optional<String> getVisitorByChatId(String chatId) {
        return visitorsRepository.findVisitorByChatId(chatId);
    }

    /**
     * Получить данные студента по номеру телефона
     * @param phoneNumber - номер телефона студента
     */
    public Optional<Visitors> getVisitorByPhone(String phoneNumber) {
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
    public boolean updateInfoOfVisitor(String phoneNumber, String surname, String patronymic) {
        Optional<Visitors> visitors = getVisitorByPhone(phoneNumber);
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
        Optional<Visitors> visitors = getVisitorByPhone(phoneNumber);
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
    public Optional<List<Visits>> getVisit(String chatId, Integer passId) {
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
                String phoneNumber = passRepository.findPhoneNumberByPassId(i);
                String chatId = visitorsRepository.findChatIDByPhoneNumber(phoneNumber);
                Optional<Visitors> visitors = getVisitorByPhone(chatId);
                visitors.ifPresent(listVisitorsToDay::add);
            }
        }
        return listVisitorsToDay;
    }
}
