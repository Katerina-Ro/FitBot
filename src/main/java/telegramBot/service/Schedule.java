package telegramBot.service;

import telegramBot.service.commandBot.receiver.utils.FindingDataUtil;

import java.time.LocalTime;
import java.util.Timer;

public class Schedule {

    public void getSchedule() {
        MyTimerTask myTimerTask = new MyTimerTask();
        Timer timer = new Timer();

        // периодичность 24 часа
        long period = 86400000L;

        LocalTime localTime = LocalTime.now();

        // время задержки перед показом
        long delay = new FindingDataUtil().getDelay(localTime);

        timer.scheduleAtFixedRate(myTimerTask, delay, period);
    }
}
