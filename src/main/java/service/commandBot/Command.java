package service.commandBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Command interface для обработки команд телеграм-бота. Отправляет сообщения типа SendMessage
 */
public interface Command {
    SendMessage execute(Update update);
}