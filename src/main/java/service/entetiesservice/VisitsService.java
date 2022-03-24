package service.entetiesservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.VisitorsRepository;

@Service
public class VisitsService {

    private final VisitorsRepository visitorsRepository;

    @Autowired
    public VisitsService(VisitorsRepository visitorsRepository) {
        this.visitorsRepository = visitorsRepository;
    }

    // получить номер телефона и фио всех, кто сегодня придет

    // получить количество занятий всего по абонементу у конкретного человека - нужен ли?

    // получить инфу по абонементу общее количество занятий + остаток

    // получить инфу отгуленных занятий по абонементу у конкретного человека
}
