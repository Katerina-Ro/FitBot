package telegramBot.service.commandBot.receiver;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.service.commandBot.COMMANDS;
import telegramBot.service.commandBot.CommandEditSendMessage;

/**
 * Класс для обработки команд {@link COMMANDS}, возвращает сообщения типа {@link EditMessageText}
 */
@Service
@Getter
public class BotCommandCallBackQueryEdit {
    private final ImmutableMap<String, CommandEditSendMessage> commandMapCommandEdit;
    private final CommandEditSendMessage infoCommand;
    private final CommandEditSendMessage yesCommand;
    private final CommandEditSendMessage noCommand;
    private final CommandEditSendMessage lessonsLeftCommand;
    private final CommandEditSendMessage unknownCommand;

    @Autowired
    public BotCommandCallBackQueryEdit(@Qualifier("infoCommand") CommandEditSendMessage infoCommand,
                                       @Qualifier("yesCommand") CommandEditSendMessage yesCommand,
                                       @Qualifier("noCommand") CommandEditSendMessage noCommand,
                                       @Qualifier("lessonsLeftCommand") CommandEditSendMessage lessonsLeftCommand,
                                       @Qualifier("unknownCommand") CommandEditSendMessage unknownCommand) {
        this.infoCommand = infoCommand;
        this.yesCommand = yesCommand;
        this.noCommand = noCommand;
        this.lessonsLeftCommand = lessonsLeftCommand;
        this.unknownCommand = unknownCommand;
        this.commandMapCommandEdit = ImmutableMap.<String, CommandEditSendMessage>builder()
                .put(COMMANDS.INFO.getCommand(), this.infoCommand)
                .put(COMMANDS.YES.getCommand(), this.yesCommand)
                .put(COMMANDS.LESSONS_LEFT.getCommand(), this.lessonsLeftCommand)
                .put(COMMANDS.NO.getCommand(), this.noCommand)
                .build();
    }

    public synchronized EditMessageText findCommand(String commandIdentifier, Update update) {
        return (commandMapCommandEdit.getOrDefault(commandIdentifier, this.unknownCommand).execute(update));
    }
}
