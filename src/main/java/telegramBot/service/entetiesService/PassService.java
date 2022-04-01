package telegramBot.service.entetiesService;

import appStudentAttedanceRecord.db.dto.DontPlanToComeToDay;
import appStudentAttedanceRecord.db.dto.PlanToComeToDay;
import lombok.Getter;
import lombok.Setter;
import telegramBot.enteties.Pass;
import telegramBot.enteties.Visitors;
import telegramBot.enteties.Visits;
import telegramBot.comparator.PassVisitsComparator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import telegramBot.repositories.PassRepository;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@Log4j2
@Service
public class PassService {
    @Getter
    @Setter
    private Map<String, PlanToComeToDay> mapVisitorsToDay = new HashMap<>();

    @Getter
    @Setter
    private Map<String, DontPlanToComeToDay> mapDontCome = new HashMap<>();

    @Getter
    private final PassRepository passRepository;

    @Autowired
    public PassService(PassRepository passRepository) {
        this.passRepository = passRepository;
    }

    public void createOrUpdatePass(Pass pass) {
        passRepository.save(pass);
    }

    public boolean clickButtonNo(Boolean isClickButtonNo) {
        return isClickButtonNo;
    }







    /**
     * Получение информации об абонементе (для админа)
     * @param numberPass - номер телефона студента
     */
    public Optional<Pass> getPassByPassNumber(Integer numberPass) {
        return passRepository.findById(numberPass);
    }

    /**
     * Получить ChatId студента по номеру абонемента
     * @param passId - номер абонемента
     */
    public Long getChatId(Integer passId) {
        return passRepository.findChatIdByPassId(passId);
    }

    /**
     * Обновление информации об абонементе
     * @param phoneNumber - номер телефона студента
     * @param numberPass - номер абонемента
     * @param dateStart - дата начала абонемента
     * @param dateEnd - дата окончания абонемента
     * @param visitLimit - количество посещений в абонементе
     * @param visits - информация о посещениях по этому абонементу
     * @return true, если обновление прошло успешно
     */
    public boolean updatePass(String phoneNumber, Integer numberPass, LocalDate dateStart, LocalDate dateEnd,
                              Integer visitLimit, @Nullable Visits visits) {
        Optional<Pass> pass = getPassByPassNumber(numberPass);
        if (pass.isPresent()) {
            pass.get().setDateStart(dateStart);
            pass.get().setDateEnd(dateEnd);
            pass.get().setVisitLimit(visitLimit);
            if (visits != null) {
                List<Visits> listVisits = pass.get().getVisits();
                listVisits.add(visits);
                pass.get().setVisits(listVisits);
            }
            return true;
        }
        return false;
    }


    /**
     * Проверка, есть ли еще день в абонементе у конкретного студента
     * @param pass - информация об абонементе
     * @return true, если есть как минимум 1 занятие
     */
    private boolean haveDayInPassByCompare(Pass pass) {
        Comparator<Pass> compPass = new PassVisitsComparator();
        int resultCompare = compPass.compare(pass, pass);
        return resultCompare >= 0;
    }

    /**
     * Проверка, полночь или нет
     * @return true, если полночь
     */
    public boolean isMidnightCurrencyDay() {
        LocalTime currencyTime = LocalTime.now();
        log.info("Получаем текущее время : " + currencyTime.toString());
        System.out.println("Получаем текущее время : " + currencyTime);
        return "23:59:00.000000000".equals(currencyTime.toString());
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
