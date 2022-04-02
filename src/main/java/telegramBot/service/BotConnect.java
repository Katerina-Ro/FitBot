package telegramBot.service;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegramBot.service.commandBot.receiver.BotCommandCallBackQueryEdit;
import telegramBot.service.commandBot.receiver.BotCommandForceReply;
import telegramBot.service.commandBot.receiver.BotCommandSendMessage;

import java.time.LocalDateTime;
import java.util.logging.Logger;

/**
 * Класс для соединения с ботом и получения от него сообщения
 */
@Service
@PropertySource(value = "classpath:botsecret.properties")
@Getter
public class BotConnect extends TelegramLongPollingBot {
    private static final Logger log = Logger.getLogger("BotConnect.class");
    final int RECONNECT_PAUSE = 10000;

    private final BotCommandSendMessage botCommandSendMessage;
    private final BotCommandCallBackQueryEdit botCommandCallbackQueryEdit;
    private final BotCommandForceReply botCommandForceReply;
    //private final BotCommandCallbackQuery botCommandCallbackQuery;

    @Setter
    @Value("${bot.name}")
    private String botUsername;
    @Setter
    @Value("${bot.token}")
    private String botToken;

    @Autowired
    public BotConnect(BotCommandSendMessage botCommandSendMessage,
                      BotCommandCallBackQueryEdit botCommandCallbackQueryEdit,
                      BotCommandForceReply botCommandForceReply) {
        this.botCommandSendMessage = botCommandSendMessage;
        this.botCommandCallbackQueryEdit = botCommandCallbackQueryEdit;
        this.botCommandForceReply = botCommandForceReply;
    }

    // настроить время polling

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null && update.hasMessage()) {
            System.out.println("update.getMessage() = " + update.getMessage());
                execute(botCommandSendMessage.findCommand(update.getMessage().getText(), update));
        } else if (update.hasCallbackQuery()) {
            System.out.println("Это CallbackQuery");
            String commandIdentifier = update.getCallbackQuery().getData();
            System.out.println("commandIdentifier = " + commandIdentifier);
            System.out.println("update " + update);
                if (update.getMessage().isReply()) {
                    System.out.println("Это isReply ");
                    execute(botCommandForceReply.findCommand(commandIdentifier, update));
                }
            System.out.println("Это не isReply ");
                execute(botCommandCallbackQueryEdit.findCommand(commandIdentifier, update));
            }

    }

    void botDisConnect() {
        LocalDateTime localDateTimeCurrency = LocalDateTime.now();
        log.info(String.format("Бот отключен %s", localDateTimeCurrency));
        System.exit(0);
    }
}

