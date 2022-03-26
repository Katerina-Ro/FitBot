package service.commandBot.receiver.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import service.commandBot.CommandEditSendMessage;
import service.commandBot.receiver.utils.SendMessageUtils;
import service.commandBot.receiver.utils.keyboard.Buttons;
import service.commandBot.receiver.utils.keyboard.MakerInlineKeyboardMarkup;
import service.entetiesService.PassService;

/**
 * Класс-Receiver команды {@link service.commandBot.COMMANDS} {@link CommandEditSendMessage}
 */
@Service
public class LessonsLeftCommand implements CommandEditSendMessage{
    @Autowired
    private final PassService passService;

    public LessonsLeftCommand(PassService passService) {
        this.passService = passService;
    }

    @Override
    public EditMessageText execute(Update update) {
        long numberUser = update.getMessage().getChatId();
        Integer exerciseLeft = passService.getPass(numberUser).getExerciseLeft();
        return SendMessageUtils.sendEditMessage(update,
                String.format("У Вас осталось %s занятий", exerciseLeft),
                MakerInlineKeyboardMarkup.get1InlineKeyboardMarkup(Buttons.getKeyBoardBackToStart()));
    }
}