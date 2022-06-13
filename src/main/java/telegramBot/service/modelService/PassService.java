package telegramBot.service.modelService;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import telegramBot.model.Pass;
import telegramBot.model.Visitors;
import telegramBot.model.Visits;
import telegramBot.repositories.IPassRepository;
import telegramBot.repositories.impl.VisitsRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class PassService {
    @Getter
    private final IPassRepository passRepository;
    private final VisitorsService visitorsService;
    private final VisitsRepository visitsRepository;

    @Autowired
    public PassService(IPassRepository passRepository, VisitorsService visitorsService, VisitsRepository visitsRepository) {
        this.passRepository = passRepository;
        this.visitorsService = visitorsService;
        this.visitsRepository = visitsRepository;
    }

    /**
     * Получение информации об актуальном абонементе
     * @param chatId - идентификатор студента в Телеграмм
     * @return список абонементов. По логике кода заложено, что может быть несколько активных абонементов.
     * Но, по идее (в реальности), должен быть только один
     */
    public Optional<List<Pass>> getActualPassByChatId(Long chatId) {
        Optional<Visitors> visitor = visitorsService.getVisitorByChatId(chatId);
        Optional<List<Pass>> passListFromDB;
        if (visitor.isPresent()) {
            String phoneNumber = visitor.get().getTelephoneNum();
            passListFromDB = passRepository.findPassByPhone(phoneNumber);
            if (passListFromDB.isPresent()) {
                List<Pass> listActualPass = new ArrayList<>();
                for (Pass p: passListFromDB.get()) {
                    if (haveDayInPassCalculate(p)) {
                        listActualPass.add(p);
                        return Optional.of(listActualPass);
                    }
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Проверка, есть ли еще день в абонементе у конкретного студента
     * @param pass - информация об абонементе
     * @return true, если есть как минимум 1 занятие
     */
    public boolean haveDayInPassCalculate(Pass pass) {
        Optional<String> countDayInPass = calculateClassesLeft(pass);
        return countDayInPass.filter(s -> Integer.parseInt(s) > 0).isPresent();
    }

    public String getClassesLeftFromAllPass(List<Pass> passList) {
        int classesLeftFromAllPass = 0;
        for (Pass p: passList) {
            Optional<String> classesLeftFromOnePass = calculateClassesLeft(p);
            if (classesLeftFromOnePass.isPresent()) {
                classesLeftFromAllPass += Integer.parseInt(classesLeftFromOnePass.get());
            }
        }
        return String.valueOf(classesLeftFromAllPass);
    }

    /**
     * Расчет оставшегося количества занятий у конкретного студента
     * @param pass - информация об абонементе
     */
    public Optional<String> calculateClassesLeft(Pass pass) {
        String classesLeft = null;
        int cumulativeTotal = 0;
        if (betweenDate(pass)) {
            Optional<List<Visits>> listVisits = visitsRepository.findAllVisitsByPassId(pass.getNumPass());
            if (listVisits.isPresent()) {
                for (Visits v: listVisits.get()) {
                    int countVisitInOneDay = v.getCountVisit();
                    cumulativeTotal += countVisitInOneDay;
                }
            }
            classesLeft = String.valueOf(pass.getVisitLimit() - cumulativeTotal);
        }
        return Optional.ofNullable(classesLeft);
    }

    /**
     * Определение, находится ли текущая дата в промежутке между началом и окончанием абонемента
     * @param pass - данные абонемента
     * @return true, если находится
     */
    public boolean betweenDate(Pass pass) {
        boolean betweenDate = false;
        LocalDate currencyDay = LocalDate.now(ZoneId.of("GMT+03:00"));
        LocalDate dateEnd = pass.getDateEnd();
        LocalDate dateStart = pass.getDateStart();

        // могут быть true и false
        boolean isEqualDateStart = currencyDay.isEqual(dateStart);

        // оба должны быть true
        boolean isBeforeDateEnd = currencyDay.isBefore(dateEnd);
        boolean isAfterDateStart = currencyDay.isAfter(dateStart);

        if ((isEqualDateStart || isAfterDateStart) && (isBeforeDateEnd) ) {
            betweenDate = true;
        }
        return betweenDate;
    }
}
