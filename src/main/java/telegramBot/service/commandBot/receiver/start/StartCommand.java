package telegramBot.service.commandBot.receiver.start;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.service.commandBot.Command;
import telegramBot.service.commandBot.COMMANDS;
import telegramBot.service.commandBot.receiver.utils.SendMessageUtils;
import telegramBot.service.commandBot.receiver.utils.keyboard.Buttons;
import telegramBot.service.entetiesService.VisitorsService;

/**
 * Стартовый класс для обработки сообщения {@link COMMANDS} - "/start" {@link Command}
 */
@Service
public class StartCommand implements Command {
    private static final String IMAGE_WAVING_HAND = String.valueOf(Character.toChars(0x1F44B));
    private static final String START_MESSAGE = "Привет " + IMAGE_WAVING_HAND + " \n Вы придете сегодня на занятие?";
    @Getter
    private static final String NO_USER_IN_DB_BY_CHAT_ID = "Привет. Это бот для опроса о посещении. Введите Ваш номер " +
            "телефона, который Вы указывали в качестве контакта, начиная с 7. Пример ввода: 79991231234";
    private final VisitorsService visitorsService;

    @Autowired
    public StartCommand(VisitorsService visitorsService) {
        this.visitorsService = visitorsService;
    }


    @Override
    //@Transactional
    public SendMessage execute(Update update)  {
        Long chatIdUser = SendMessageUtils.getChatIdUser(update);
        if(!visitorsService.havPhoneNumber(chatIdUser)) {
            return messageError(update);
        }
        SendMessage sendMessage = SendMessageUtils.sendMessage(update,START_MESSAGE, false);
        sendMessage.setReplyMarkup(Buttons.getKeyBoardStartMenu());
        return sendMessage;
    }

    private SendMessage messageError(Update update){
        return SendMessageUtils.sendMessage(update, NO_USER_IN_DB_BY_CHAT_ID, true);
    }
}