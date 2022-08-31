package telegramBot.service.modelService;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supportTable.ComeToDay;
import telegramBot.repositories.IComeToDayRepository;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class ComeToDayService {
    private final IComeToDayRepository comeToDayRepository;

    @Autowired
    public ComeToDayService(IComeToDayRepository comeToDayRepository) {
        this.comeToDayRepository = comeToDayRepository;
    }

    public boolean existThisComeToday(ComeToDay comeToDay) {
        Optional<List<ComeToDay>> listComeToday = comeToDayRepository.getComeToDay();
        if (listComeToday.isPresent()) {
            for (ComeToDay c : listComeToday.get()) {
                boolean equalCharId = comeToDay.getChatId().equals(c.getChatId());
                boolean equalPhoneNumber = comeToDay.getTelephoneNum().equals(c.getTelephoneNum());
                boolean equalDateVisit = comeToDay.getCurrencyDate().isEqual(c.getCurrencyDate());

                if (equalCharId && equalPhoneNumber && equalDateVisit) {
                    return true;
                }
            }
        }
        return false;
    }

    public void createComeToDay(ComeToDay comeToDay) {
        comeToDayRepository.createComeToDay(comeToDay);
    }
}
