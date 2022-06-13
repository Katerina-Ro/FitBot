package telegramBot.service;

import telegramBot.service.commandBot.receiver.utils.FindingDataUtil;

import java.time.LocalTime;
import java.util.Timer;

public class Schedule {

    public void getSchedule() {
        MyTimerTask myTimerTask = new MyTimerTask();
        Timer timer = new Timer();

        // периодичность 24 часа
        long period = 86400000;
        // время задержки перед показом
        long delay;

        LocalTime localTime = LocalTime.now();
        if (FindingDataUtil.isEventTime(localTime)) {
            delay = 0L;
            System.out.println(delay + "миллисекунд");
        } else {
            delay = new FindingDataUtil().getDelay(localTime);
            System.out.println(delay + " delay");
        }
        timer.scheduleAtFixedRate(myTimerTask, delay, period);
    }
}
