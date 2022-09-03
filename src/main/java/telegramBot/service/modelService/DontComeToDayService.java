package telegramBot.service.modelService;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supportTable.DontComeToDay;
import telegramBot.repositories.IDontComeToDayRepository;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class DontComeToDayService {
    private final IDontComeToDayRepository dontComeToDayRepository;

    @Autowired
    public DontComeToDayService(IDontComeToDayRepository dontComeToDayRepository) {
        this.dontComeToDayRepository = dontComeToDayRepository;
    }

    public boolean existThisComeToday(DontComeToDay dontComeToDay) {
        Optional<List<DontComeToDay>> listComeToday = dontComeToDayRepository.getDontComeToDay();
        if (listComeToday.isPresent()) {
            for (DontComeToDay c : listComeToday.get()) {
                boolean equalCharId = dontComeToDay.getChatId().equals(c.getChatId());
                boolean equalPhoneNumber = dontComeToDay.getTelephoneNum().equals(c.getTelephoneNum());
                boolean equalDateVisit = dontComeToDay.getCurrencyDate().isEqual(c.getCurrencyDate());

                if (equalCharId && equalPhoneNumber && equalDateVisit) {
                    return true;
                }
            }
        }
        return false;
    }

    public void createDontComeToDay(DontComeToDay dontComeToDay) {
        dontComeToDayRepository.createDontComeToDay(dontComeToDay);
    }
}
