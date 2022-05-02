package telegramBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;

@SpringBootApplication
public class AskBot {


    public static void main(String[] args) throws TelegramApiException, IOException {
        /*ScheduledExecutorService scheduled = Executors.newSingleThreadScheduledExecutor();
        scheduled.scheduleAtFixedRate(new MyTimerTask(), 20, 24, TimeUnit.HOURS);*/

        LocalDateTime localDate = LocalDateTime.of(LocalDate.of(2022, 5, 2),
                LocalTime.of(14, 50, 0));
        Date dateFirstLaunch = Date.from(localDate.atZone(ZoneId.of("GMT+03:00")).toInstant());
        MyTimerTask myTimerTask = new MyTimerTask();
        Timer timer = new Timer();

        // периодичность 24 часа
        long period = 86400000;
        timer.scheduleAtFixedRate(myTimerTask, dateFirstLaunch, period);

        new TelegramBotsApi(DefaultBotSession.class);
        //ApiContextInitializer.init();
        SpringApplication.run(AskBot.class, args);
    }
}