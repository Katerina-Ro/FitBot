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
    private static final String QUESTION_MARK = String.valueOf(Character.toChars(0x2753));
    private static final String INFO_MESSAGE = "Для чего этот бот" +
            QUESTION_MARK +
            " \n " +
            " \n " +
            "Бот предназначен для опроса студентов о посещении занятий. \n " + " \n " +
            "Нажимая на кнопку 'Да', Вы подтверждаете, что придете на занятие. Занятие будет списано " +
            "с Вашего абонемента. \n " + " \n " +
            "Нажимая на кнопку 'Нет', Вы предупреждаете, что сегодня не придете. Занятие не будет списано \n ";

    @Override
    public EditMessageText execute(Update update) {
        return SendMessageUtils.sendEditMessage(update, INFO_MESSAGE,
                MakerInlineKeyboardMarkup.get1InlineKeyboardMarkup(Buttons.getKeyBoardBackToStart()));
    }
}