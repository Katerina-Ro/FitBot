package telegramBot.service.commandBot.receiver;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.service.commandBot.COMMANDS;
import telegramBot.service.commandBot.Command;

/**
 * Класс - стартовое меню {@link COMMANDS} возвращает сообщения типа {@link SendMessage}
 */
@Service
@Getter
public class BotCommandSendMessage {
    private final ImmutableMap<String, Command> commandMapSendMessage;
    private final Command startCommand;

    @Autowired
    public BotCommandSendMessage(@Qualifier("startCommand") Command startCommand) {
        this.startCommand = startCommand;
        this.commandMapSendMessage = ImmutableMap.<String, Command>builder()
                .put(COMMANDS.START.getCommand(), this.startCommand)
                .put(COMMANDS.BUTTON_BACK_TO_START.getCommand(), this.startCommand)
                .build();
    }

    public synchronized SendMessage findCommand(String commandIdentifier, Update update) {
        return (commandMapSendMessage.getOrDefault(commandIdentifier, startCommand).execute(update));
    }
}
