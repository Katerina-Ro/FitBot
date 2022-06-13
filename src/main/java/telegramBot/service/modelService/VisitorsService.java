package telegramBot.service.modelService;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import telegramBot.model.Visitors;
import telegramBot.repositories.IVisitorsRepository;

import java.util.Optional;

@Service
public class VisitorsService {
    @Getter
    private final IVisitorsRepository visitorsRepository;
    @Autowired
    public VisitorsService(IVisitorsRepository visitorsRepository) {
        this.visitorsRepository = visitorsRepository;
    }

    /**
     * Проверка, есть ли у этого chatId номер телефона в базе
     */
    public boolean havPhoneNumber(Long chatId) {
        Optional<String> phoneNumberInDB = visitorsRepository.findTelephoneNumByChatId(chatId);
        return phoneNumberInDB.isPresent();
    }

    /**
     * Получить информацию о студенте по chatId
     * @param chatId - идентификатор студента в Телеграмме
     */
    public Optional<Visitors> getVisitorByChatId(Long chatId) {
        return visitorsRepository.findVisitorByChatId(chatId);
    }
}
