package telegramBot.service.entetiesService;

import lombok.Getter;
import lombok.Setter;
import telegramBot.enteties.Pass;
import telegramBot.enteties.Visits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import telegramBot.repositories.VisitsRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VisitsService {
    @Getter
    private final VisitsRepository visitsRepository;

    private static final String message = "Нет занятий в абонементе";
    private static final String messagePassException = "Абонемент не занесен в базу или произошел разрыв соединения " +
            "с базой данных. Попробуйте еще раз";

    @Autowired
    public VisitsService(VisitsRepository visitsRepository) {
        this.visitsRepository = visitsRepository;
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
            visit.get().setDateVisit(Date.valueOf(dateVisit));
            visit.get().setCountVisit(calculateCountVisit(visit.get().getCountVisit()));
            visitsRepository.save(visit.get());
            return true;
        }
        return false;
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
        Date sqlDate = Date.valueOf(currencyDay);
        return visitsRepository.findAllPassIdByCurrencyDay(sqlDate);
    }

    private Integer calculateCountVisit(Integer countVisit) {
        return ++countVisit;
    }




}
