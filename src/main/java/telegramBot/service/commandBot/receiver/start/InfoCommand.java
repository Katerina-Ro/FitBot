package telegramBot.service.commandBot.receiver.start;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.service.commandBot.CommandEditSendMessage;
import telegramBot.service.commandBot.COMMANDS;
import telegramBot.service.commandBot.receiver.utils.SendMessageUtils;
import telegramBot.service.commandBot.receiver.utils.keyboard.Buttons;
import telegramBot.service.commandBot.receiver.utils.keyboard.MakerInlineKeyboardMarkup;

/**
 * Класс-Receiver команды {@link COMMANDS} {@link CommandEditSendMessage}
 */
@Service
public class InfoCommand implements CommandEditSendMessage {
    private static final String EXCLAMATION_MARK = String.valueOf(0x0021);
    private static final String INFO_MESSAGE = "Бот предназначен для опроса студентов о посещении занятий. \n " +
            "Нажимая на кнопку 'Да', Вы подтверждаете, что придете на занятие. Занятие автоматически спишется " +
            "с Вашего абонемента. \n " +
            "Нажимая на кнопку 'Нет', Вы предупреждаете, что сегодня не придете. Занятие не будет списано \n " +
            "Обратите внимание" + EXCLAMATION_MARK + " , если Вы не ответите ни Да, ни Нет до 23.59 текущего дня, то занятие " +
            "будет автоматически списано с Вашего абонемента.";

    @Override
    public EditMessageText execute(Update update) {
        return SendMessageUtils.sendEditMessage(update, INFO_MESSAGE,
                MakerInlineKeyboardMarkup.get1InlineKeyboardMarkup(Buttons.getKeyBoardBackToStart()));
    }
}