package telegramBot.service.commandBot.receiver.start;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.service.commandBot.COMMANDS;
import telegramBot.service.commandBot.CommandEditSendMessage;
import telegramBot.service.commandBot.receiver.utils.SendMessageUtils;
import telegramBot.service.commandBot.receiver.utils.keyboard.Buttons;
import telegramBot.service.modelService.VisitorsService;

/**
 * Класс главного менб, когда проверка телефона пройдена {@link COMMANDS} - "Назад, в главное меню"
 * {@link CommandEditSendMessage}
 */
@Service
public class MainMenuCommand implements CommandEditSendMessage {
    private static final String IMAGE_WAVING_HAND = String.valueOf(Character.toChars(0x1F44B));
    private static final String START_MESSAGE = "Привет " + IMAGE_WAVING_HAND + " \n Вы придете сегодня на занятие?";
    @Getter
    private static final String NO_USER_IN_DB_BY_CHAT_ID = "Привет. Это бот для опроса о посещении. Введите Ваш номер " +
            "телефона, который Вы указывали в качестве контакта, начиная с 7. Пример ввода: 79991231234";
    private final VisitorsService visitorsService;

    @Autowired
    public MainMenuCommand(VisitorsService visitorsService) {
        this.visitorsService = visitorsService;
    }


    @Override
    //@Transactional
    public EditMessageText execute(Update update)  {
        //Long chatIdUser = SendMessageUtils.getChatIdUser(update);
        return SendMessageUtils.sendEditMessage(update,START_MESSAGE,
                Buttons.getKeyBoardStartMenu());
    }
}
