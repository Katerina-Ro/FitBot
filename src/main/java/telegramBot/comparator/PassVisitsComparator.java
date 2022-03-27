package telegramBot.comparator;

import db.enteties.Pass;

import java.util.Comparator;

public class PassVisitsComparator implements Comparator<Pass> {
    @Override
    public int compare(Pass pass1, Pass pass2) {
        return pass1.getVisitLimit().compareTo(pass2.getVisits().getCountVisit());
    }
}
