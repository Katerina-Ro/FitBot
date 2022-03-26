package service.commandBot.receiver.start;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import service.commandBot.Command;
import service.commandBot.receiver.utils.SendMessageUtils;
import service.commandBot.receiver.utils.keyboard.Buttons;
import service.entetiesService.VisitorsService;

/**
 * Стартовый класс для обработки сообщения {@link service.commandBot.COMMANDS} - "/start" {@link Command}
 */
@Service
public class StartCommand implements Command {
    private static final String IMAGE_WAVING_HAND = String.valueOf(Character.toChars(0x1F44B));
    private static final String START_MESSAGE = "Привет " + IMAGE_WAVING_HAND + " \n Это бот для опроса о посещении";
    @Getter
    private static final String NO_USER_IN_DB_BY_CHAT_ID = "Введите Ваш номер телефона, который Вы указывали " +
            "в качестве контакта, начиная с 7. Пример ввода: 7 9991231234";
    private final VisitorsService visitorsService;

    @Autowired
    public StartCommand(VisitorsService visitorsService) {
        this.visitorsService = visitorsService;
    }

    @Override
    public SendMessage execute(Update update)  {
        long numberUser = update.getMessage().getChatId();
        if(!visitorsService.havPhoneNumber(numberUser)) {
            return messageError(update);
        }
        return SendMessageUtils.sendMessage(update,START_MESSAGE, false)
                .setReplyMarkup(Buttons.getKeyBoardStartMenu());
    }

    private SendMessage messageError(Update update){
        return SendMessageUtils.sendMessage(update, NO_USER_IN_DB_BY_CHAT_ID, true);
    }
}