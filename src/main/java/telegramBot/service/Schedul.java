package telegramBot.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;

public class Schedul {

    public void getSchedul() {
        LocalDateTime localDate = LocalDateTime.of(LocalDate.of(2022, 5, 4),
                LocalTime.of(11, 45, 0));
        Date dateFirstLaunch = Date.from(localDate.atZone(ZoneId.of("GMT+03:00")).toInstant());

        MyTimerTask myTimerTask = new MyTimerTask();
        Timer timer = new Timer();

        // периодичность 24 часа
        long period = 10000;//86400000;
        timer.scheduleAtFixedRate(myTimerTask, dateFirstLaunch, period);
    }
}
