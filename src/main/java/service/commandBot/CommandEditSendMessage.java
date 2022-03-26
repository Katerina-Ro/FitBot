package service.commandBot;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Command interface для обработки команд телеграм-бота. Отправляет сообщения типа EditMessageText
 */
public interface CommandEditSendMessage {
    EditMessageText execute(Update update);
}