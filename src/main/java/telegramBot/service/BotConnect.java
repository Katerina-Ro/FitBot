package telegramBot.service;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegramBot.AskBot;
import telegramBot.service.commandBot.receiver.BotCommandCallBackQueryEdit;
import telegramBot.service.commandBot.receiver.BotCommandSendMessage;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * Класс для соединения с ботом и получения от него сообщения
 */
@Service
@PropertySource(value = "classpath:botsecret.properties")
@Getter
public class BotConnect extends TelegramLongPollingBot {
    private static final Logger log = Logger.getLogger("BotConnect.class");
    private Timer mTimer = new Timer ();
    //private MyTimerTask myTimerTask = new MyTimerTask ();
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
    @Value("${bot.token}")
    private String timeout;

    @Autowired
    public BotConnect(BotCommandSendMessage botCommandSendMessage,
                      BotCommandCallBackQueryEdit botCommandCallbackQueryEdit) {
        this.botCommandSendMessage = botCommandSendMessage;
        this.botCommandCallbackQueryEdit = botCommandCallbackQueryEdit;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null && update.hasMessage()) {
            try {
                execute(botCommandSendMessage.findCommand(update.getMessage().getText(), update));
            } catch (Exception e) {
                /*
                try {
                    Thread.sleep(RECONNECT_PAUSE);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                    return;
                } */
                try {
                    botConnect();
                } catch (TelegramApiException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (update.hasCallbackQuery()) {
            String commandIdentifier = update.getCallbackQuery().getData();
            try {
                execute(botCommandCallbackQueryEdit.findCommand(commandIdentifier, update));
            } catch (Exception e) {
                try {
                    Thread.sleep(RECONNECT_PAUSE);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                    return;
                }
                try {
                    botConnect();
                } catch (TelegramApiException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void botConnect() throws TelegramApiException {

            /*
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);*/
        //new TelegramBotsApi(DefaultBotSession.class);
            SpringApplication.run(AskBot.class);
            /*
        } catch (TelegramApiException e) {
            try {
                Thread.sleep(RECONNECT_PAUSE);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                return;
            }
            botConnect();
        } */
    }

    /*
    public void starterTimer() {
        mTimer.schedule(myTimerTask, 1000);
    }

    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            botConnect();
        }
    } */
}

