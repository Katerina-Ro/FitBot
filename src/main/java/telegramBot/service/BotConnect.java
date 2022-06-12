package telegramBot.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegramBot.service.commandBot.COMMANDS;
import telegramBot.service.commandBot.receiver.BotCommandCallBackQuery;
import telegramBot.service.commandBot.receiver.BotCommandCallBackQueryEdit;
import telegramBot.service.commandBot.receiver.BotCommandSendMessage;
import telegramBot.service.commandBot.receiver.utils.FindingDataUtil;
import telegramBot.service.commandBot.receiver.utils.SendMessageUtils;

import java.net.MalformedURLException;
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
    private final BotCommandCallBackQuery botCommandCallBackQuery;

    @Setter
    @Value("${bot.name}")
    private String botUsername;
    @Setter
    @Value("${bot.token}")
    private String botToken;
    /*
    @Setter
    @Value("${bot.timeout}")
    private String timeOut; */

    @Autowired
    public BotConnect(BotCommandSendMessage botCommandSendMessage,
                      BotCommandCallBackQueryEdit botCommandCallbackQueryEdit,
                      BotCommandCallBackQuery botCommandCallBackQuery) throws MalformedURLException {
        this.botCommandSendMessage = botCommandSendMessage;
        this.botCommandCallbackQueryEdit = botCommandCallbackQueryEdit;
        this.botCommandCallBackQuery = botCommandCallBackQuery;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null && update.hasMessage()) {
            SendMessageUtils.removeForceReplyKeyboard();
            try {
                log.info("TelegramAPI started");
                String commandIdentifier = update.getMessage().getText();
                if (FindingDataUtil.isPhoneNumber(update.getMessage().getText())) {
                   commandIdentifier = update.getMessage().getReplyToMessage().getText();
                }
                execute(botCommandSendMessage.findCommand(commandIdentifier, update));
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
                }
                //botConnect();
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
                }
               // botConnect();
            }
        } else if (update.hasCallbackQuery()) {
            String commandIdentifier = update.getCallbackQuery().getData();
            if (COMMANDS.BUTTON_BACK_TO_START.getCommand().equals(commandIdentifier)) {
                try {
                    execute(botCommandCallBackQuery.findCommand(commandIdentifier, update));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
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
                    // botConnect();
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
            }

            }
        }
    }

    void botDisConnect() {
        LocalDateTime localDateTimeCurrency = LocalDateTime.now();
        log.info(String.format("Бот отключен %s", localDateTimeCurrency));
        System.exit(0);
    }
/*
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
    } */
}

