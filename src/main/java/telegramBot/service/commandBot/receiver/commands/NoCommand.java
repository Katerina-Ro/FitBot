package telegramBot.service.commandBot.receiver.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.service.commandBot.CommandEditSendMessage;
import telegramBot.service.commandBot.COMMANDS;
import telegramBot.service.commandBot.receiver.utils.SendMessageUtils;
import telegramBot.service.commandBot.receiver.utils.keyboard.Buttons;
import telegramBot.service.commandBot.receiver.utils.keyboard.MakerInlineKeyboardMarkup;
import telegramBot.service.entetiesService.PassService;

/**
 * Класс-Receiver команды {@link COMMANDS} {@link CommandEditSendMessage}
 */
@Service
public class NoCommand implements CommandEditSendMessage {
    private static final String NO_MESSAGE = "Приходите в следующий раз. Хорошего дня";
    private final PassService passService;

    @Autowired
    public NoCommand(PassService passService) {
        this.passService = passService;
    }

    @Override
    public EditMessageText execute(Update update) {
        // добавить в список тех, кто в текущий день не приходит
        passService.clickButtonNo(true);
        return SendMessageUtils.sendEditMessage(update, NO_MESSAGE,
                MakerInlineKeyboardMarkup.get1InlineKeyboardMarkup(Buttons.getKeyBoardBackToStart()));
    }
}

