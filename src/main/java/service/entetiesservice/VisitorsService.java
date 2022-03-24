package service.entetiesservice;

import enteties.Visitors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.VisitorsRepository;

import javax.annotation.Nullable;
import java.util.Optional;

@Service
public class VisitorsService {

    private final VisitorsRepository visitorsRepository;

    @Autowired
    public VisitorsService(VisitorsRepository visitorsRepository) {
        this.visitorsRepository = visitorsRepository;
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
        return visitorsRepository.existsByTelephoneNum(phoneNumber.trim());
    }

    /**
     * Заносим номер телефона в базу данных по chatId
     */
    public void createVisitors(Long chatId, String phoneNumber, String name, @Nullable String surname) {
        Visitors visitor = new Visitors();
        visitor.setChatId(chatId);
        visitor.setTelephoneNum(phoneNumber);
        visitor.setName(name);
        if (surname != null) {
            visitor.setSurname(surname);
        }
        visitorsRepository.save(visitor);
    }
}
