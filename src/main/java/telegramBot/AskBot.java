package telegramBot;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.telegram.telegrambots.ApiContextInitializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScans({@ComponentScan("db.repositories"), @ComponentScan("telegramBot")})
//@EnableJpaRepositories
public class AskBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(AskBot.class, args);
    }
}