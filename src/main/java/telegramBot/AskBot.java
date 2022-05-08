package telegramBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegramBot.service.Schedul;

import java.io.IOException;

@SpringBootApplication
public class AskBot {

    public static void main(String[] args) throws TelegramApiException, IOException {
        /*ScheduledExecutorService scheduled = Executors.newSingleThreadScheduledExecutor();
        scheduled.scheduleAtFixedRate(new MyTimerTask(), 20, 24, TimeUnit.HOURS);*/

        new TelegramBotsApi(DefaultBotSession.class);
        //ApiContextInitializer.init();
        SpringApplication.run(AskBot.class, args);

        new Schedul().getSchedul();
    }
}