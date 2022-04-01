package telegramBot.service.commandBot.receiver.commands;

import appStudentAttedanceRecord.db.service.PlanToComeService;
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
    private final PlanToComeService planToConeService;
    @Autowired
    public LessonsLeftCommand(PlanToComeService planToConeService) {
        this.planToConeService = planToConeService;
    }

    @Override
    @Transactional
    public EditMessageText execute(Update update) {
        long numberUser = update.getMessage().getChatId();
        Optional<Pass> pass = planToConeService.getActualPassByChatId(numberUser);
        Integer classesLeft = null;
        if (pass.isPresent()) {
            classesLeft = planToConeService.calculateClassesLeft(pass.get());
        }
        return SendMessageUtils.sendEditMessage(update,
                String.format("У Вас осталось %s занятий", classesLeft),
                MakerInlineKeyboardMarkup.get1InlineKeyboardMarkup(Buttons.getKeyBoardBackToStart()));
    }
}