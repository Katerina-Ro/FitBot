package service.commandBot.receiver;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import service.commandBot.CommandEditSendMessage;
import service.commandBot.receiver.start.StartCommand;

/**
 * Класс для обработки ForceReplyMessage, возвращает ответ типа SendMessage
 */
@Service
@Getter
public class BotCommandForceReply {
    private final ImmutableMap<String, CommandEditSendMessage> commandMapForceReply;
    private final CommandEditSendMessage phoneNumber;

    @Autowired
    public BotCommandForceReply(@Qualifier("phoneNumber") CommandEditSendMessage phoneNumber){
        this.phoneNumber = phoneNumber;
        this.commandMapForceReply = ImmutableMap.<String, CommandEditSendMessage>builder()
                .put(StartCommand.getNO_USER_IN_DB_BY_CHAT_ID(), this.phoneNumber)
                .build();
    }
    public EditMessageText findCommand(String commandIdentifier, Update update) {
            return (commandMapForceReply.get(commandIdentifier).execute(update));
    }
}
