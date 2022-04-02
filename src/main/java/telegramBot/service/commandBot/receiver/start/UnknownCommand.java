package telegramBot.service.commandBot.receiver.start;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.service.commandBot.CommandEditSendMessage;
import telegramBot.service.commandBot.receiver.utils.SendMessageUtils;
import telegramBot.service.commandBot.receiver.utils.keyboard.Buttons;

/**
 * Класс-Receiver неизвестной команды {@link CommandEditSendMessage}
 */
@Service
public class UnknownCommand implements CommandEditSendMessage {
    private static final String MESSAGE_UNKNOWNCOMMAND = "Выберите одну из предложенных кнопок";

    @Override
    public synchronized EditMessageText execute(Update update) {
        return SendMessageUtils.sendEditMessage (update, MESSAGE_UNKNOWNCOMMAND,
                Buttons.getKeyBoardStartMenu());
    }
}
