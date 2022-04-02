package telegramBot.service;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegramBot.service.commandBot.receiver.BotCommandCallBackQueryEdit;
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

    @Setter
    @Value("${bot.name}")
    private String botUsername;
    @Setter
    @Value("${bot.token}")
    private String botToken;
    @Setter
    @Value("${bot.timeout}")
    private String timeOut;

    @Autowired
    public BotConnect(BotCommandSendMessage botCommandSendMessage,
                      BotCommandCallBackQueryEdit botCommandCallbackQueryEdit) {
        this.botCommandSendMessage = botCommandSendMessage;
        this.botCommandCallbackQueryEdit = botCommandCallbackQueryEdit;
    }

    // настроить время polling

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null && update.hasMessage()) {
            try {
                log.info("TelegramAPI started. Look for messages");
                execute(botCommandSendMessage.findCommand(update.getMessage().getText(), update));
            } catch (TelegramApiException e) {
                log.info("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: "
                        + e.getMessage());
                System.out.println("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: "
                        + e.getMessage());
                try {
                    Thread.sleep(RECONNECT_PAUSE);
                } catch (InterruptedException e1) {
                    System.out.println("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: "
                            + e.getMessage());
                    e1.printStackTrace();
                    return;
                }
                    botConnect();
            } catch (Exception e) {
                log.info("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: "
                        + e.getMessage());
                System.out.println("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: "
                        + e.getMessage());
                try {
                    Thread.sleep(RECONNECT_PAUSE);
                } catch (InterruptedException e1) {
                    System.out.println("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: "
                            + e.getMessage());
                    e1.printStackTrace();
                    return;
                }
                botConnect();
            }
        } else if (update.hasCallbackQuery()) {
            String commandIdentifier = update.getCallbackQuery().getData();
            try {
                execute(botCommandCallbackQueryEdit.findCommand(commandIdentifier, update));
            } catch (TelegramApiException e) {
                log.info("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: "
                        + e.getMessage());
                try {
                    Thread.sleep(RECONNECT_PAUSE);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                    return;
                }
                botConnect();
            } catch (Exception e) {
                log.info("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: "
                        + e.getMessage());
                System.out.println("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: "
                        + e.getMessage());
                try {
                    Thread.sleep(RECONNECT_PAUSE);
                } catch (InterruptedException e1) {
                    System.out.println("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: "
                            + e.getMessage());
                    e1.printStackTrace();
                    return;
                }
                botConnect();
            }
        }
    }

    void botDisConnect() {
        LocalDateTime localDateTimeCurrency = LocalDateTime.now();
        log.info(String.format("Бот отключен %s", localDateTimeCurrency));
        System.exit(0);
    }

    public void botConnect() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            log.info("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: "
                    + e.getMessage());
            try {
                Thread.sleep(RECONNECT_PAUSE);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                return;
            }
            botConnect();
        }
    }
}

