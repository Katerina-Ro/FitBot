package service.entetiesService;

import enteties.Pass;
import enteties.Visitors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.PassRepository;
import repositories.VisitorsRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
@Service
public class PassService {

    private final PassRepository passRepository;
    private final VisitorsRepository visitorsRepository;

    @Autowired
    public PassService(PassRepository passRepository, VisitorsRepository visitorsRepository) {
        this.passRepository = passRepository;
        this.visitorsRepository = visitorsRepository;
    }

    /**
     * Вычет занятия из абонемента, если кдиент нажал "Да"
     */
    public boolean deductVisitIfYes(Long chatId) {
        Pass pass = getPass(chatId);
        if (haveDayInPass(chatId)) {
            pass.setExerciseLeft(pass.getExerciseLeft() - 1);
            passRepository.save(pass);
            LocalDate localDate = LocalDate.now(ZoneId.of("Europe/Moscow"));

            // зафиксировать этот день в визит
            // написать лог

            return true;
        }
        // Сообщение, что занятий больше нет
     return false;
    }

    // прибавить занятие (через админа)

    /**
     * Вычет заняти, если до 23:59:00.000000000 не было ответа (ни "Да", ни "Нет")
     */
    public void deductVisitWithOutAnswer(Long chatId) {
        if (!deductVisitIfYes(chatId) && haveDayInPass(chatId) && isMidnightCurrencyDay()) {
            deductVisitIfYes(chatId);
        }
    }

    private boolean haveDayInPass(Long chatId) {
        Pass pass = getPass(chatId);
        return pass.getExerciseLeft() > 0;
    }

    /**
     * Получение информации об абонементе
     * @param chatId - уникальный номер пользователя в telegram bot
     * @throws NoSuchElementException
     */
    public Pass getPass(Long chatId) throws NoSuchElementException {
        Optional<Visitors> visitor = visitorsRepository.findById(chatId);
        // возможно, дописать, чтобы кидал ошибку, а в ошибке на финализации сохранял на время в память запись и
        // после вычитал или отправлять эту инфу админу, чтобы ручками вычитал
        return visitor.map(Visitors::getGymPass).orElseThrow();
    }

    private boolean isMidnightCurrencyDay() {
        // Получаем текущее время
        LocalTime time = LocalTime.now();
        System.out.println("Получаем текущее время : " + time);
        // получить дату и время полночи
        // если localDate и полночь совпадают, то тру
        return "23:59:00.000000000".equals(time);
    }
}
