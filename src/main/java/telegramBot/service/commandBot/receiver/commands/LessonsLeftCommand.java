package telegramBot.service.commandBot.receiver.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.model.Pass;
import telegramBot.service.commandBot.COMMANDS;
import telegramBot.service.commandBot.CommandEditSendMessage;
import telegramBot.service.commandBot.receiver.utils.SendMessageUtils;
import telegramBot.service.commandBot.receiver.utils.keyboard.Buttons;
import telegramBot.service.commandBot.receiver.utils.keyboard.MakerInlineKeyboardMarkup;
import telegramBot.service.modelService.PassService;

import java.util.List;
import java.util.Optional;

/**
 * Класс-Receiver команды {@link COMMANDS} {@link CommandEditSendMessage}
 */
@Service
public class LessonsLeftCommand implements CommandEditSendMessage{
    private final PassService passService;
    @Autowired
    public LessonsLeftCommand(PassService passService) {
        this.passService = passService;
    }

    @Override
   // @Transactional
    public EditMessageText execute(Update update) {
        Long numberUser = SendMessageUtils.getChatIdUser(update);
        Optional<List<Pass>> passList = passService.getActualPassByChatId(numberUser);
        String classesLeft = String.valueOf(0);
        if (passList.isPresent()) {
            classesLeft = String.format("Ждем Вас сегодня на занятиях. У Вас осталось %s занятий ",
                    passService.getClassesLeftFromAllPass(passList.get()));
        }

        return SendMessageUtils.sendEditMessage(update,
                classesLeft,
                MakerInlineKeyboardMarkup.get1InlineKeyboardMarkup(Buttons.getKeyBoardBackToStart()));
    }
}