package service.commandBot.receiver.commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import service.commandBot.CommandEditSendMessage;
import service.commandBot.receiver.utils.SendMessageUtils;
import service.commandBot.receiver.utils.keyboard.Buttons;
import service.commandBot.receiver.utils.keyboard.MakerInlineKeyboardMarkup;

/**
 * Класс-Receiver команды {@link service.commandBot.COMMANDS} {@link CommandEditSendMessage}
 */
@Service
public class LessonsLeftCommand implements CommandEditSendMessage{
    private static final String NO_MESSAGE = "Приходите в следующий раз. Хорошего дня";

    @Override
    public EditMessageText execute(Update update) {
        // добавить в список тех, кто в текущий день не приходит
        return SendMessageUtils.sendEditMessage(update, NO_MESSAGE,
                MakerInlineKeyboardMarkup.get1InlineKeyboardMarkup(Buttons.getKeyBoardBackToStart()));
    }
}