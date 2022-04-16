package telegramBot.service.commandBot.receiver;
/*
import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.service.commandBot.COMMANDS;
import telegramBot.service.commandBot.Command;
import telegramBot.service.commandBot.CommandEditSendMessage;
import telegramBot.service.commandBot.receiver.start.StartCommand;

/**
 * Класс-распорядитель ответов на ForceReply {@link COMMANDS} возвращает сообщения типа {@link SendMessage}

@Service
@Getter
public class BotCommandEditSendMessage {
    private final ImmutableMap<String, CommandEditSendMessage> commandMapSendMessage;
    private final CommandEditSendMessage phoneNumberCommand;
    private final CommandEditSendMessage unknownCommand;

    @Autowired
    public BotCommandEditSendMessage(@Qualifier("phoneNumberCommand") CommandEditSendMessage phoneNumberCommand,
                                     @Qualifier("unknownCommand") CommandEditSendMessage unknownCommand) {
        this.phoneNumberCommand = phoneNumberCommand;
        this.unknownCommand = unknownCommand;
        this.commandMapSendMessage = ImmutableMap.<String, CommandEditSendMessage>builder()
                .put(StartCommand.getNO_USER_IN_DB_BY_CHAT_ID(), this.phoneNumberCommand)
                .build();
    }

    public EditMessageText findCommand(String commandIdentifier, Update update) {
        return (commandMapSendMessage.getOrDefault(commandIdentifier, unknownCommand).execute(update));
    }
}
 */