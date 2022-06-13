package telegramBot.service;

import java.util.Timer;

public class Schedule {

    public void getSchedule() {
        MyTimerTask myTimerTask = new MyTimerTask();
        // ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        Timer timer = new Timer();

        // периодичность 24 часа   86400000
        long period = 100000;
        // время задержки перед показом
        long delay = 0L;
        timer.scheduleAtFixedRate(myTimerTask, delay, period);
    }
}
