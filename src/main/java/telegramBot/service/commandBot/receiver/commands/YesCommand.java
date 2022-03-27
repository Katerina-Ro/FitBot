package telegramBot.service.commandBot.receiver.commands;

import telegramBot.enteties.Pass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class YesCommand implements CommandEditSendMessage {
    @Autowired
    private final PassService passService;

    public YesCommand(PassService passService) {
        this.passService = passService;
    }

    @Override
    @Transactional
    public EditMessageText execute(Update update) {
        long numberUser = update.getMessage().getChatId();
        Optional<Pass> pass = passService.getActualPassByChatId(numberUser);
        Integer classesLeft = null;
        if (pass.isPresent()) {
            boolean isSuccess = passService.deductVisitIfYes(pass.get());
            if (!isSuccess) {
               // если неуспешно, то сформировать инфу для админа
            }
            classesLeft = passService.calculateClassesLeft(pass.get());
        }
        return SendMessageUtils.sendEditMessage(update,
                String.format("Ждем Вас сегодня на занятиях. У Вас осталось %s занятий", classesLeft),
                MakerInlineKeyboardMarkup.get1InlineKeyboardMarkup(Buttons.getKeyBoardBackToStart()));
    }
}
