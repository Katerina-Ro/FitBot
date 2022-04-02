package telegramBot.service.commandBot.receiver.commands;

import dto.DontPlanToComeToDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegramBot.enteties.Visitors;
import telegramBot.service.commandBot.COMMANDS;
import telegramBot.service.commandBot.CommandEditSendMessage;
import telegramBot.service.commandBot.receiver.utils.SendMessageUtils;
import telegramBot.service.commandBot.receiver.utils.keyboard.Buttons;
import telegramBot.service.commandBot.receiver.utils.keyboard.MakerInlineKeyboardMarkup;
import telegramBot.service.entetiesService.PassService;
import telegramBot.service.entetiesService.VisitorsService;

import java.util.Optional;

/**
 * Класс-Receiver команды {@link COMMANDS} {@link CommandEditSendMessage}
 */
@Service
public class NoCommand implements CommandEditSendMessage {
    private static final String NO_MESSAGE = "Приходите в следующий раз. Хорошего дня";
    private final VisitorsService visitorsService;
    private final PassService passService;

    @Autowired
    public NoCommand(VisitorsService visitorsService, PassService passService) {
        this.visitorsService = visitorsService;
        this.passService = passService;
    }

    @Override
    @Transactional
    public EditMessageText execute(Update update) {
        Long numberUser = SendMessageUtils.getChatIdUser(update);
        // добавить в список тех, кто в текущий день не приходит
        DontPlanToComeToDay dontPlanToComeToDay = new DontPlanToComeToDay();
        Optional<Visitors> visitors = visitorsService.getVisitor(numberUser);
        if (visitors.isPresent()) {
            Visitors v = visitors.get();
            dontPlanToComeToDay.setChatId(v.getChatId());
            dontPlanToComeToDay.setName(v.getName());
            dontPlanToComeToDay.setTelephoneNum(v.getTelephoneNum());
            passService.getMapDontCome().put(v.getTelephoneNum(), dontPlanToComeToDay);
        }

        passService.clickButtonNo(true);
        return SendMessageUtils.sendEditMessage(update, NO_MESSAGE,
                MakerInlineKeyboardMarkup.get1InlineKeyboardMarkup(Buttons.getKeyBoardBackToStart()));
    }
}

