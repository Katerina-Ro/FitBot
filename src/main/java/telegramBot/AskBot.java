package telegramBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegramBot.service.Schedule;

@SpringBootApplication
public class AskBot {

    public static void main(String[] args) throws TelegramApiException {
        new TelegramBotsApi(DefaultBotSession.class);
        SpringApplication.run(AskBot.class, args);

        new Schedule().getSchedule();
    }

    public void turnOffApp(boolean flag) {
        if (flag) {
            System.exit(0);
        }
    }
}