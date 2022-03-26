package service.commandBot.receiver.start;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import service.commandBot.CommandEditSendMessage;
import service.commandBot.receiver.utils.SendMessageUtils;
import service.commandBot.receiver.utils.keyboard.Buttons;

/**
 * Класс-Receiver неизвестной команды {@link CommandEditSendMessage}
 */
@Service
public class UnknownCommand implements CommandEditSendMessage {
    private static final String MESSAGE_UNKNOWNCOMMAND = "Выберите одну из предложенных кнопок";

    @Override
    public EditMessageText execute(Update update) {
        return SendMessageUtils.sendEditMessage (update, MESSAGE_UNKNOWNCOMMAND,
                Buttons.getKeyBoardStartMenu());
    }
}
