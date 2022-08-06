package telegramBot.service.commandBot.receiver;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.service.commandBot.COMMANDS;
import telegramBot.service.commandBot.CommandEditSendMessage;

/**
 * Класс для обработки команд {@link COMMANDS}, возвращает сообщения типа {@link SendMessage}
 */
@Service
@Getter
public class BotCommandCallBackQuery {
    private final ImmutableMap<String, CommandEditSendMessage> commandMapSendMessage;
    private final CommandEditSendMessage mainMenuCommand;

    public BotCommandCallBackQuery(@Qualifier("mainMenuCommand")CommandEditSendMessage mainMenuCommand) {
        this.mainMenuCommand = mainMenuCommand;
        this.commandMapSendMessage = ImmutableMap.<String, CommandEditSendMessage>builder()
                .put(COMMANDS.BUTTON_BACK_TO_START.getCommand(), this.mainMenuCommand)
                .build();
    }

    public synchronized EditMessageText findCommand(String commandIdentifier, Update update) {
        return (commandMapSendMessage.get(commandIdentifier).execute(update));
    }
}
