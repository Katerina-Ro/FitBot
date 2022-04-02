package telegramBot;

import appStudentAttedanceRecord.db.dto.PlanToComeToDay;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.telegram.telegrambots.ApiContextInitializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
//@ComponentScans({@ComponentScan("db.repositories"), @ComponentScan("telegramBot")})
//@EnableJpaRepositories
public class AskBot {
    public static void main(String[] args) throws TelegramApiException {
        //ApiContextInitializer.init();

       new TelegramBotsApi(DefaultBotSession.class);
        SpringApplication.run(AskBot.class, args);
    }
}