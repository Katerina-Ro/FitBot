package telegramBot.service;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.apache.log4j.Logger;
import telegramBot.service.commandBot.receiver.BotCommandCallBackQueryEdit;
import telegramBot.service.commandBot.receiver.BotCommandForceReply;
import telegramBot.service.commandBot.receiver.BotCommandSendMessage;
import telegramBot.service.commandBot.receiver.utils.FindingDataUtil;

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
    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null && update.hasMessage()) {
            System.out.println("Start update.getMessage() = " + update.getMessage());
            try {
                log.info("TelegramAPI started. Look for messages");
                System.out.println("Это начало");
                String commandIdentifier = update.getMessage().getText();
                System.out.println("В начале commandIdentifier = " + commandIdentifier);
                System.out.println("Это номер телефона? " + FindingDataUtil.isPhoneNumber(commandIdentifier));
                if (FindingDataUtil.isPhoneNumber(commandIdentifier)) {
                    execute(botCommandSendMessage.phoneCommand(update));
                }
                execute(botCommandSendMessage.findCommand(commandIdentifier, update));
            } catch (TelegramApiException e) {
                log.error("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: " + e.getMessage());

                e.printStackTrace();
            } catch (Exception e) {
                log.error("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: " + e.getMessage());
                e.printStackTrace();
                restartOnUpdateReceived(update);
            }

        } else if (update.hasCallbackQuery()) {
            System.out.println("Это CallbackQuery");
            String commandIdentifier = update.getCallbackQuery().getData();
            System.out.println("commandIdentifier = " + commandIdentifier);
            System.out.println("update " + update);
            /*
                if (update.getMessage().isReply()) {
                    System.out.println("Это isReply ");
                    execute(botCommandForceReply.findCommand(commandIdentifier, update));
                } */
            System.out.println("Это не isReply ");
            try {
                execute(botCommandCallbackQueryEdit.findCommand(commandIdentifier, update));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                restartOnUpdateReceived(update);
            }
        }
    }

    void botDisConnect() {
        LocalDateTime localDateTimeCurrency = LocalDateTime.now();
        log.info(String.format("Бот отключен %s", localDateTimeCurrency));
        System.exit(0);
    }

    public void restartOnUpdateReceived(Update update) {
        onUpdateReceived(update);
    }
}

