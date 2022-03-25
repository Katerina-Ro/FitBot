package service.entetiesservice;

import enteties.Pass;
import enteties.Visits;
import exception.NoPassException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.PassRepository;
import repositories.VisitorsRepository;
import repositories.VisitsRepository;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class VisitsService {
    private final VisitorsRepository visitorsRepository;
    private final VisitsRepository visitsRepository;
    private final PassService passService;

    private static final String message = "Нет занятий в абонементе";
    private static final String messagePassException = "Абонемент не занесен в базу или произошел разрыв соединения " +
            "с базой данных. Попробуйте еще раз";

    @Autowired
    public VisitsService(VisitorsRepository visitorsRepository, VisitsRepository visitsRepository, PassRepository passRepository, PassService passService) {
        this.visitorsRepository = visitorsRepository;
        this.visitsRepository = visitsRepository;
        this.passService = passService;
    }

    public boolean createVisit(Long chatId, LocalDate dateVisit) throws NoSuchElementException, NoPassException {
        Optional<Visits> visit = getVisit(chatId);
        if (visit.isPresent()) {
            visit.get().setDateVisit(dateVisit);
            visit.get().setCountVisit(calculateCountVisit(visit.get().getCountVisit()));
            visitsRepository.save(visit.get());
            return true;
        }
        throw new NoPassException(messagePassException);
    }

    private Integer calculateCountVisit(Integer countVisit) {
        return ++countVisit;
    }

    public Optional<Visits> getVisit(Long chatId) {
        Pass pass = passService.getPass(chatId);
        return visitsRepository.findById(pass.getGymPass());
    }
}
