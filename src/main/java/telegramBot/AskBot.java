package telegramBot;

import org.springframework.boot.SpringApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AskBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(AskBot.class, args);
    }
}