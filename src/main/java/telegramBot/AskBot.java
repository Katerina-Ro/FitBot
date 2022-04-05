package telegramBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceTransactionManagerAutoConfiguration.class})
public class AskBot {
    public static void main(String[] args) throws TelegramApiException {
        ApiContextInitializer.init();
      // new TelegramBotsApi(DefaultBotSession.class);
        SpringApplication.run(AskBot.class, args);
    }
}