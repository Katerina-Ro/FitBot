package telegramBot.service.commandBot.receiver.commands;

import org.springframework.transaction.annotation.Transactional;
import telegramBot.enteties.Pass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.service.commandBot.CommandEditSendMessage;
import telegramBot.service.commandBot.COMMANDS;
import telegramBot.service.commandBot.receiver.utils.SendMessageUtils;
import telegramBot.service.commandBot.receiver.utils.keyboard.Buttons;
import telegramBot.service.commandBot.receiver.utils.keyboard.MakerInlineKeyboardMarkup;
import telegramBot.service.entetiesService.PassService;

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
    //@Transactional
    public synchronized EditMessageText execute(Update update) {
        Long numberUser = SendMessageUtils.getChatIdUser(update);
        Optional<Pass> pass = passService.getActualPassByChatId(numberUser);
        Optional<String> classesLeft;
        classesLeft = pass.map(value -> String.format("Ждем Вас сегодня на занятиях. " + "У Вас осталось %s занятий",
                    passService.calculateClassesLeft(value))).or(() -> Optional.of("Нет информации о Вашем абонементе. " +
                    "Обратитесь к администратору"));
        return SendMessageUtils.sendEditMessage(update,
                classesLeft.get(),
                MakerInlineKeyboardMarkup.get1InlineKeyboardMarkup(Buttons.getKeyBoardBackToStart()));
    }
}