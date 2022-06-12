package telegramBot.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Schedule {

    public void getSchedule() {
        MyTimerTask myTimerTask = new MyTimerTask();
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        // периодичность 24 часа   86400000
        long period = 1;
        // время задержки перед показом
        long delay = 0L;
        scheduler.scheduleAtFixedRate(myTimerTask, delay, period, TimeUnit.MINUTES);
    }
}
